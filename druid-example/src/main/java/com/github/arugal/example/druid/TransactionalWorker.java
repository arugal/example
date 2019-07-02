package com.github.arugal.example.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: zhangwei
 * @date: 2019-07-02/11:14
 */
@Service
public class TransactionalWorker {


    /**
     * 建表语句
     * DROP TABLE IF EXISTS `transactional_worker`;
     * CREATE TABLE `transactional_worker` (
     *  `id` int(10) unsigned NOT NULL,
     *   PRIMARY KEY (`id`)
     *   ENGINE=InnoDB DEFAULT CHARSET=utf8;"
     *   )
     */

    @Autowired
    private DataSource dataSource;

    @Transactional
    public void init() throws SQLException{
        execute("delete from transactional_worker;");
    }

    @Transactional
    public void run() throws SQLException {
        execute("insert into transactional_worker (id) values (1)");
        try {

            execute("insert into transactional_worker (id) values (2)");
            execute("insert into transactional_worker (id) values (2)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void execute(String sql) throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        Statement statement = connection.createStatement();
        try {
            statement.execute(sql);
        } finally {
            if(statement != null) {
                statement.close();
            }
        }

    }
}
