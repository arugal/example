package com.github.arugal.flink.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * @author zhangwei
 */
@Slf4j
public class CheckpointExample {


	private static final String CLASS_NAME = CheckpointExample.class.getSimpleName();

	public static void main(String[] args) throws Exception {
		log.info("{}:starting...", CLASS_NAME);

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		Properties prop = new Properties();

		prop.put("bootstart.servers", "example.com:9200");
		prop.put("group.id", "flink-stackoverflow-checkpoint");
		prop.put("auto.offset.reset", "latest");
		prop.put("enable.auto.commit", "false");

		FlinkKafkaConsumer010<String> source = new FlinkKafkaConsumer010<>("input-topic", new SimpleStringSchema(), prop);

		env.enableCheckpointing(1000, CheckpointingMode.EXACTLY_ONCE);

		env.addSource(source)
			.keyBy((any) -> 1)
			.flatMap(new StatefulMapper())
			.print();

		env.execute(CLASS_NAME);
	}

	public static class StatefulMapper extends RichFlatMapFunction<String, String> {

		private static final long serialVersionUID = 8978044066161942292L;

		private transient ValueState<Integer> state;

		@Override
		public void flatMap(String s, Collector<String> collector) throws Exception {
			Integer currentSate = state.value();

			currentSate += 1;
			collector.collect(String.format("%s:(%s:%d)", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), s, currentSate));

			state.update(currentSate);
		}

		@Override
		public void open(Configuration parameters) throws Exception {
			ValueStateDescriptor<Integer> descriptor = new ValueStateDescriptor<Integer>("CheckpointExample", TypeInformation.of(Integer.class), 0);
			state = getRuntimeContext().getState(descriptor);
		}
	}

}
