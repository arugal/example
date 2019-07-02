package com.github.arigal.example.druid;

import com.github.arugal.example.druid.TransactionalWorker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

/**
 * @author: zhangwei
 * @date: 2019-07-02/11:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.github.arugal.example")
public class TransactionalTest {

    @Autowired
    private TransactionalWorker transactionalWorker;

    @Before
    public void up() throws SQLException {
        transactionalWorker.init();
    }

    @Test
    public void test() throws SQLException {
        transactionalWorker.run();
    }

}
