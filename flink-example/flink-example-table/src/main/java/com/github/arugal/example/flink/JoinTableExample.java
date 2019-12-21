package com.github.arugal.example.flink;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.flink.api.common.io.DefaultInputSplitAssigner;
import org.apache.flink.api.common.io.RichInputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.GenericInputSplit;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.sinks.AppendStreamTableSink;
import org.apache.flink.table.sinks.TableSink;
import org.apache.flink.table.sinks.UpsertStreamTableSink;
import org.apache.flink.table.sources.StreamTableSource;
import org.apache.flink.types.Row;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.github.arugal.example.flink.Example.wrapStreamTableEnv;


/**
 * @author zhangwei
 */
public class JoinTableExample {


    public static void main(String[] args) throws Exception {

        wrapStreamTableEnv((env, tEnv) -> {

            tEnv.registerTableSource("account", new Source(Account.getTableSchema(), Account.getDatas()));
            tEnv.registerTableSource("order", new Source(Order.getTableSchema(), Order.getDatas()));

            tEnv.registerTableSink("accountOrder", new Sink(AccountOrder.getTableSchema()));

//            Table table = tEnv.sqlQuery("select * from account");
//            tEnv.toAppendStream(table, Account.class).print();
            tEnv.sqlUpdate("insert into accountOrder select account.id, account.name, `order`.shop " +
                "from account left join `order` on account.id = `order`.id where `order`.id is not null");
        });
    }


    public static class Source implements StreamTableSource<Row> {


        private TableSchema schema;

        private List<Object[]> datas;

        private RowTypeInfo rowTypeInfo;

        public Source(TableSchema schema, List<Object[]> datas) {
            this.schema = schema;
            this.datas = datas;
            this.rowTypeInfo = new RowTypeInfo(schema.getFieldTypes(), schema.getFieldNames());
        }

        @Override
        public TableSchema getTableSchema() {
            return schema;
        }

        @Override
        public boolean isBounded() {
            return true;
        }

        @Override
        public DataStream<Row> getDataStream(StreamExecutionEnvironment execEnv) {
            return execEnv.createInput(new SourceInputFormat(datas, rowTypeInfo), getReturnType()).name(explainSource());
        }

        @Override
        public TypeInformation<Row> getReturnType() {
            return rowTypeInfo;
        }
    }

    public static class SourceInputFormat extends RichInputFormat<Row, InputSplit> implements ResultTypeQueryable<Row> {

        private static final long serialVersionUID = -1941084216786916132L;


        private List<Object[]> data;

        private Iterator<Object[]> iterator;

        private RowTypeInfo rowTypeInfo;

        public SourceInputFormat(List<Object[]> data, RowTypeInfo rowTypeInfo) {
            this.data = data;
            this.rowTypeInfo = rowTypeInfo;
        }

        @Override
        public void configure(Configuration parameters) {

        }

        @Override
        public BaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException {
            return cachedStatistics;
        }

        @Override
        public InputSplit[] createInputSplits(int minNumSplits) throws IOException {
            return new GenericInputSplit[]{new GenericInputSplit(0, 1)};
        }

        @Override
        public InputSplitAssigner getInputSplitAssigner(InputSplit[] inputSplits) {
            return new DefaultInputSplitAssigner(inputSplits);
        }

        @Override
        public void openInputFormat() throws IOException {
            iterator = data.iterator();
        }

        @Override
        public void open(InputSplit split) throws IOException {
            iterator = data.iterator();
        }

        @Override
        public boolean reachedEnd() throws IOException {
            return iterator != null && iterator.hasNext();
        }

        @Override
        public Row nextRecord(Row reuse) throws IOException {
            Object[] datas = iterator.next();
            for (int i = 0; i < datas.length; i++) {
                reuse.setField(i, datas[i]);
            }
            return reuse;
        }

        @Override
        public void closeInputFormat() throws IOException {
//            iterator = null;
        }

        @Override
        public void close() throws IOException {
//            iterator = null;
        }

        @Override
        public TypeInformation<Row> getProducedType() {
            return rowTypeInfo;
        }
    }

    public static class SinkAppend implements AppendStreamTableSink<Row> {

        private TableSchema schema;

        public SinkAppend(TableSchema schema) {
            this.schema = schema;
        }

        @Override
        public void emitDataStream(DataStream<Row> dataStream) {
            dataStream.addSink(new SinkFunction<Row>() {

                private static final long serialVersionUID = -4007884666837475492L;

                @Override
                public void invoke(Row value, Context context) throws Exception {
                    System.out.println(value.getField(1));
                }
            });
        }

        @Override
        public TableSink<Row> configure(String[] fieldNames, TypeInformation<?>[] fieldTypes) {
            TableSchema.Builder builder = TableSchema.builder();
            for (int i = 0; i < fieldNames.length; i++) {
                builder.field(fieldNames[i], fieldTypes[i]);
            }
            return new SinkAppend(builder.build());
        }

        @Override
        public TableSchema getTableSchema() {
            return schema;
        }
    }

    public static class Sink implements UpsertStreamTableSink<Row> {

        private TableSchema schema;

        public Sink(TableSchema schema) {
            this.schema = schema;
        }

        @Override
        public void setKeyFields(String[] keyFields) {
        }

        @Override
        public void setIsAppendOnly(Boolean isAppendOnly) {
        }

        @Override
        public TypeInformation<Row> getRecordType() {
            return new RowTypeInfo(schema.getFieldTypes(), schema.getFieldNames());
        }

        @Override
        public void emitDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {
            dataStream.addSink(new SinkFunction<Tuple2<Boolean, Row>>() {
                private static final long serialVersionUID = -2482330112306581813L;

                @Override
                public void invoke(Tuple2<Boolean, Row> value, Context context) throws Exception {
                    System.out.println(value.f1);
                }
            });
        }

        @Override
        public TableSink<Tuple2<Boolean, Row>> configure(String[] fieldNames, TypeInformation<?>[] fieldTypes) {
            TableSchema.Builder builder = TableSchema.builder();
            for (int i = 0; i < fieldNames.length; i++) {
                builder.field(fieldNames[i], fieldTypes[i]);
            }
            return new Sink(builder.build());
        }

        @Override
        public TableSchema getTableSchema() {
            return schema;
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Account {

        public Long id;
        public String name;

        public static TableSchema getTableSchema() {
            TableSchema.Builder builder = TableSchema.builder();
            builder.field("id", Types.LONG);
            builder.field("name", Types.STRING);

            return builder.build();
        }


        public static List<Object[]> getDatas() {
            return Arrays.asList(
                new Object[] {1L, "zw1"},
                new Object[] {2L, "zw2"},
                new Object[] {3L, "zw3"},
                new Object[] {4L, "zw4"},
                new Object[] {5L, "zw5"},
                new Object[] {6L, "zw6"}
            );
        }

        public static DataStream<Row> getDataStream(StreamExecutionEnvironment execEnv) {
            return execEnv.fromCollection(Arrays.asList(
                Row.of(1L, "zw1"),
                Row.of(2L, "zw2"),
                Row.of(3L, "zw3"),
                Row.of(4L, "zw4"),
                Row.of(5L, "zw5"),
                Row.of(6L, "zw6")
            ));
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Order {
        public Long id;
        public String shop;

        public static TableSchema getTableSchema() {
            TableSchema.Builder builder = TableSchema.builder();

            builder.field("id", Types.LONG);
            builder.field("shop", Types.STRING);

            return builder.build();
        }

        public static List<Object[]> getDatas() {
            return Arrays.asList(
                new Object[] {1L, "a"},
                new Object[] {2L, "b"},
                new Object[] {3L, "c"},
                new Object[] {4L, "d"},
                new Object[] {5L, "e"},
                new Object[] {6L, "f"}
            );
        }

        public static DataStream<Row> getDataStream(StreamExecutionEnvironment execEnv) {
            return execEnv.fromCollection(Arrays.asList(
                Row.of(1L, "a"),
                Row.of(2L, "b"),
                Row.of(3L, "c"),
                Row.of(4L, "d"),
                Row.of(5L, "e"),
                Row.of(6L, "f")
            ));
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class AccountOrder {
        public Long id;
        public String name;
        public String shop;

        public static TableSchema getTableSchema() {
            TableSchema.Builder builder = TableSchema.builder();

            builder.field("id", Types.LONG);
            builder.field("name", Types.STRING);
            builder.field("shop", Types.STRING);

            return builder.build();
        }
    }
}
