package com.github.arugal.example.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.factories.TableFactoryService;
import org.apache.flink.table.factories.TableSourceFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei
 */
public class FileSystemExample {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        Map<String, String> map = new HashMap<>();
        map.put("connector.type", "filesystem");
        map.put("format.type", "csv");
        map.put("connector.property-version", "1");
        map.put("format.property-version", "1");

        map.put("connector.path", "file:///Users/zhangwei/Downloads/mapping_csv.csv");
        map.put("format.fields.0.type", "LONG");
        map.put("format.fields.0.name", "id");
        map.put("format.fields.1.type", "VARCHAR");
        map.put("format.fields.1.name", "name");

        map.put("format.field-delimiter", ",");
        map.put("format.line-delimiter", "\r\n");

        map.put("schema.0.type", "LONG");
        map.put("schema.0.name", "id");
        map.put("schema.1.type", "VARCHAR");
        map.put("schema.1.name", "name");

        TableSourceFactory factory = TableFactoryService.find(TableSourceFactory.class, map);

        tEnv.registerTableSource("fac", factory.createTableSource(map));

        Table table = tEnv.sqlQuery("select * from fac");

        tEnv.toAppendStream(table, Fac.class).print();
        env.execute();
    }

    public static class Fac {
        public Long id;
        public String name;

        @Override
        public String toString() {
            return "Fac{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
        }
    }
}
