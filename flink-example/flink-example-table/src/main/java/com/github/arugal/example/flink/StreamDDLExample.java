package com.github.arugal.example.flink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Elasticsearch;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author zhangwei
 */
public class StreamDDLExample {

	private static final Logger LOG = LoggerFactory.getLogger(StreamDDLExample.class);

	private static String esHost;

	private static Integer esPort;

	public static void main(String[] args) throws Exception {
		final ParameterTool params = ParameterTool.fromArgs(args);

		final String command;
		final int loop;
		final String checkpointDataUri;
		final boolean checkpoint;

		try {
			command = params.get("command", "api");
			loop = params.getInt("loop", -1);
			checkpointDataUri = params.get("checkpointDataUri", "file:///var/flink-1.9.0/flink-checkpoints");
			checkpoint = params.getBoolean("checkpoint", true);
			esHost = params.get("eshost", "192.168.2.105");
			esPort = params.getInt("esPort", 59_200);
		} catch (Exception e) {
			throw e;
		}


		StringBuilder configStringBuilder = new StringBuilder();
		final String lineSeparator = System.getProperty("line.separator");
		configStringBuilder
			.append("Job configuration").append(lineSeparator)
			.append("Command=").append(command).append(lineSeparator)
			.append("Source loop=").append(loop).append(lineSeparator)
			.append("FS state path=").append(checkpointDataUri).append(lineSeparator)
			.append("Check point=").append(checkpoint).append(lineSeparator)
			.append("ES host=").append(esHost).append(lineSeparator)
			.append("ES port=").append(esPort).append(lineSeparator);

		LOG.info(configStringBuilder.toString());

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

		if (checkpoint) {
			env.setStateBackend(new FsStateBackend(checkpointDataUri));

			CheckpointConfig config = env.getCheckpointConfig();
			config.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
			config.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
			config.setCheckpointInterval(60_000);
		}

		switch (command) {
			case "api": {
				api(tEnv, loop);
				break;
			}
			case "ddl": {
				ddl(tEnv, loop);
				break;
			}
			default: {
				api(tEnv, loop);
			}
		}

		DataStream<DayTariffView> views = newSource(env, loop, checkpoint);

		Table table = tEnv.fromDataStream(views, "meterId, rawPap");

		tEnv.sqlUpdate("INSERT INTO Sink SELECT meterId, rawPap FROM " + table);

		env.execute();
	}

	private static void ddl(StreamTableEnvironment tEnv, int loop) throws Exception {

		tEnv.sqlUpdate(
			"CREATE TABLE Sink (\n" +
				"  meterId INT,\n" +
				"  rawPap DOUBLE\n" +
				") WITH (\n" +
				"  'connector.type' = 'elasticsearch', -- required: specify this table type is elasticsearch\n" +
				"  'connector.version' = '6',          -- required: valid connector versions are \"6\"\n" +
				"  'connector.hosts.0.hostname' = '" + esHost + "',  -- required: one or more Elasticsearch hosts to connect to\n" +
				"  'connector.hosts.0.port' = '" + esPort + "',\n" +
				"  'connector.hosts.0.protocol' = 'http',\n" +
				"  'connector.index' = 'sink-ddl',       -- required: Elasticsearch index\n" +
				"  'connector.document-type' = 'sink-ddl',  -- required: Elasticsearch document type\n" +
				"  'update-mode' = 'append',            -- optional: update mode when used as table sink.           \n" +
				"  'format.type' = 'json',                   -- required: specify the format type\n" +
				"  'format.json-schema' =                    -- or by using a JSON schema which parses to DECIMAL and TIMESTAMP\n" +
				"    '{\n" +
				"      \"type\": \"object\",\n" +
				"      \"properties\": {\n" +
				"        \"meterId\": {\n" +
				"          \"type\": \"number\"\n" +
				"        },\n" +
				"        \"rawPap\": {\n" +
				"          \"type\": \"number\"\n" +
				"        }\n" +
				"      }\n" +
				"    }'\n" +
				")"
		);
	}

	private static void api(StreamTableEnvironment tEnv, int loop) throws Exception {
		tEnv.connect(new Elasticsearch()
			.version("6")                      // required: valid connector versions are "6"
			.host(esHost, esPort, "http")   // required: one or more Elasticsearch hosts to connect to
			.index("sink-api")                  // required: Elasticsearch index
			.documentType("sink-api")        // required: Elasticsearch document type
		).withFormat(new Json()
			.jsonSchema("{" +
				"  type: 'object'," +
				"  properties: {" +
				"    meterId: {" +
				"      type: 'number'" +
				"    }," +
				"    rawPap: {" +
				"      type: 'number'" +
				"    }" +
				"  }" +
				"}"))
			.withSchema(new Schema()
				.field("meterId", Types.INT)
				.field("rawPap", Types.DOUBLE))
			.inAppendMode()
			.registerTableSink("Sink");
	}

	private static DataStream<DayTariffView> newSource(StreamExecutionEnvironment env, final int loop, boolean checkpoint) {
		if (checkpoint) {
			return env.addSource(new DayTariffViewSource(loop)).uid("day-tariff-view-source");
		} else {
			return env.addSource(new DayTariffViewSourceCheckpointed(loop)).uid("day-tariff-view-source");
		}
	}


	public static class DayTariffViewSource implements SourceFunction<DayTariffView> {

		private static final long serialVersionUID = 3708022424116298688L;
		private volatile boolean isRunning = true;

		private final Random random = new Random();

		private final int loop;

		protected volatile int index = 0;

		public DayTariffViewSource(int loop) {
			this.loop = loop;
		}

		@Override
		public void run(SourceContext<DayTariffView> sourceContext) throws Exception {
			while (isRunning && (loop < 1 || index < loop)) {
				synchronized (sourceContext.getCheckpointLock()) {
					++index;
					if (index == Integer.MAX_VALUE) {
						index = 0;
					}
					sourceContext.collect(new DayTariffView(index, random.nextDouble()));
				}
				Thread.sleep(1_000 * 30);
			}
		}

		@Override
		public void cancel() {
			isRunning = false;
		}
	}

	public static class DayTariffViewSourceCheckpointed extends DayTariffViewSource implements ListCheckpointed<Integer> {

		private static final long serialVersionUID = -5796507240857636405L;

		public DayTariffViewSourceCheckpointed(int loop) {
			super(loop);
		}

		@Override
		public List<Integer> snapshotState(long checkpointId, long timestamp) throws Exception {
			return Collections.singletonList(index);
		}

		@Override
		public void restoreState(List<Integer> list) throws Exception {
			for (Integer integer : list) {
				index = integer;
			}
		}
	}
}
