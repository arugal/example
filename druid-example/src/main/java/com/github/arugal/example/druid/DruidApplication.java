package com.github.arugal.example.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author: zhangwei
 * @date: 21:33/2019-03-14
 */
@SpringBootApplication
public class DruidApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(DruidApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("select 1");
        statement.close();
        connection.close();
    }
}
