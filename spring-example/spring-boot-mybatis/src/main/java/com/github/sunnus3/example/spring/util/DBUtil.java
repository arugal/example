package com.github.sunnus3.example.spring.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author: zhangwei
 * @date: 10:23/2019-01-24
 */
public class DBUtil {

    private static final String CREATE_TABLE = "DROP TABLE IF EXISTS COMPANY;" +
            " CREATE TABLE COMPANY " +
            "(ID INT PRIMARY KEY     NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " AGE            INT     NOT NULL, " +
            " ADDRESS        CHAR(50), " +
            " SALARY         REAL)";

    private static final String[] INSERTS = {
            "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (1, 'Paul', 32, 'California', 20000.00 );",
            "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (2, 'Allen', 25, 'Texas', 15000.00 );",
            "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );",
            "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"};

    public static void init(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:example.db");
            System.out.println("Opened database successfully");
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE);
            for(String insert : INSERTS){
                statement.executeUpdate(insert);
            }
            statement.close();
            connection.close();
            System.out.println("Init database successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
