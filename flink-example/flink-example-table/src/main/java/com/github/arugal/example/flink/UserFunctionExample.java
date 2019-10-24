package com.github.arugal.example.flink;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.functions.TableFunction;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author zhangwei
 */
public class UserFunctionExample {

    public static void main(String[] args) throws Exception {
        String[] finalArgs = args.length < 1 ? new String[] {"joinEmail"} : args;
        wrapStreamTableEnv(tEnv -> {
            switch (finalArgs[0]) {
                case "hashCode": {
                    tEnv.registerFunction("hashCode", new HashCode());

                    Table result = tEnv.sqlQuery("select id, product, hashCode(product) as hashCode, amount, `count` from record");

                    tEnv.toAppendStream(result, HashCodeRecord.class).print();
                    return;
                }
                case "joinEmail": {
                    tEnv.registerFunction("joinEmail", new JoinEmail());

                    Table result = tEnv.sqlQuery("select id, product, amount, email, `count` from record left join lateral table(joinEmail(id)) as T(email) on true");

                    tEnv.toAppendStream(result, EmailRecord.class).print();
                    return;
                }
                case "priceCalculate": {
                    tEnv.registerFunction("priceCalculate", new PriceCalculate());

                    Table result = tEnv.sqlQuery("select id, product, amount, `count`, priceCalculate(amount, `count`) as price from record");

                    tEnv.toAppendStream(result, PriceRecord.class).print();

                }
            }
        });
    }


    public static void wrapStreamTableEnv(Consumer<StreamTableEnvironment> consumer) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        DataStream<Record> dataStream = env.fromCollection(Arrays.asList(
            new Record(1L, "beer", 3, 2),
            new Record(1L, "diaper", 4, 1),
            new Record(3L, "rubber", 2, 10)));

        tEnv.registerDataStream("record", dataStream, "id, product, amount, count");

        consumer.accept(tEnv);

        env.execute();
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Record {
        public Long id;
        public String product;
        public int amount;
        public int count;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class HashCodeRecord extends Record {
        public int hashCode;

        @Override
        public String toString() {
            return "HashCodeRecord{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", amount=" + amount +
                ", count=" + count +
                ", hashCode=" + hashCode +
                '}';
        }
    }

    public static class HashCode extends ScalarFunction {

        private static final long serialVersionUID = 3436813543144452044L;

        private final int factor;

        public HashCode(int factor) {
            this.factor = factor;
        }

        public HashCode() {
            this.factor = 12;
        }

        public int eval(Object val) {
            return val.hashCode() * factor;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailRecord extends Record {
        public String email;

        @Override
        public String toString() {
            return "EmailRecord{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", amount=" + amount +
                ", count=" + count +
                ", email='" + email + '\'' +
                '}';
        }
    }

    public static class JoinEmail extends TableFunction<Tuple1<String>> {

        private static final long serialVersionUID = -8908723542395237369L;

        public void eval(Long id) {
            collect(new Tuple1<>("user@gmial.com"));
        }

        @Override
        public void close() throws Exception {
            System.out.println("close");
        }


        /**
         * Setup method for user-defined function. It can be used for initialization work.
         * By default, this method does nothing.
         */
        public void open(FunctionContext context) throws Exception {
            System.out.println("open");
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class PriceRecord extends Record {
        public Integer price;

        @Override
        public String toString() {
            return "PriceRecord{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", amount=" + amount +
                ", count=" + count +
                ", price=" + price +
                '}';
        }
    }

    public static class PriceCalculate extends ScalarFunction {

        private static final long serialVersionUID = -3754415062875850887L;

        public int eval(int amount, int count) {
            return amount * count;
        }
    }
}
