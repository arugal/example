package com.github.arugal.example.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: zhangwei
 * @date: 22:39/2019-03-28
 */
@Component
public class TransactionalService {

    @Autowired
    private DataSource dataSource;

    /**
     * 如果当前没有事务，就新建一个事务，如果已经存在一个事务，加入这个事务
     * @throws SQLException
     */
    @Transactional
    public void requiredSelect() throws SQLException {
        call();
    }


    /**
     * 支持当前事务，如果当前没有事务，就以非事务的方式执行
     * @throws SQLException
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supportsSelect() throws SQLException {
        call();
    }

    /**
     * 不实用 spring 事务
     * @throws SQLException
     */
    public void noTransactionSelect() throws SQLException {
        call();
    }

    private void call() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("select 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(statement != null){
                statement.close();
            }
        }
    }
}
