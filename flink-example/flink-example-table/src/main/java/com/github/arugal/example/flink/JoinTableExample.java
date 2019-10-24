package com.github.arugal.example.flink;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.sinks.AppendStreamTableSink;
import org.apache.flink.table.sinks.TableSink;
import org.apache.flink.table.sinks.UpsertStreamTableSink;
import org.apache.flink.table.sources.StreamTableSource;
import org.apache.flink.types.Row;

import java.util.Arrays;
import java.util.function.Function;

import static com.github.arugal.example.flink.Example.wrapStreamTableEnv;


/**
 * @author zhangwei
 */
public class JoinTableExample {


    public static void main(String[] args) throws Exception {

        wrapStreamTableEnv((env, tEnv) -> {

            tEnv.registerTableSource("account", new Source(Account.getTableSchema(), Account::getDataStream));
            tEnv.registerTableSource("order", new Source(Order.getTableSchema(), Order::getDataStream));


            tEnv.registerTableSink("accountOrder", new Sink(AccountOrder.getTableSchema()));

            tEnv.sqlUpdate("insert into accountOrder select account.id, account.name, `order`.shop " +
                "from account left join `order` on account.id = `order`.id where `order`.id is not null");
        });
    }


    public static class Source implements StreamTableSource<Row> {


        private TableSchema schema;

        private Function<StreamExecutionEnvironment, DataStream<Row>> function;

        public Source(TableSchema schema, Function<StreamExecutionEnvironment, DataStream<Row>> function) {
            this.schema = schema;
            this.function = function;
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
            return function.apply(execEnv);
        }
        @Override
        public TypeInformation<Row> getReturnType() {
            return new RowTypeInfo(schema.getFieldTypes(), schema.getFieldNames());
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
            System.out.println("keyFields:" + keyFields);
        }

        @Override
        public void setIsAppendOnly(Boolean isAppendOnly) {
            System.out.println("isAppendOnly:" + isAppendOnly);
        }

        @Override
        public TypeInformation<Row> getRecordType() {
            return new RowTypeInfo(schema.getFieldTypes(), schema.getFieldNames());
        }

        @Override
        public void emitDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {
            dataStream.addSink(new SinkFunction<Tuple2<Boolean, Row>>() {
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
