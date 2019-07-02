package com.github.arugal.example.druid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zhangwei
 * @date: 21:33/2019-03-14
 */
@SpringBootApplication
public class DruidApplication implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TransactionalWorker transactionalWorker;

    protected static volatile boolean stop = false;

    public static void main(String[] args) {
        SpringApplication.run(DruidApplication.class, args);
    }

    private static AtomicInteger SUM_INDEX = new AtomicInteger(0);

    private static final int THREAD_SIZE = 200;

    private static final int SUM_MAX = THREAD_SIZE * 50;

    private static final CountDownLatch countDownLatch = new CountDownLatch(THREAD_SIZE);


    //    @Bean
    public ApplicationRunner trRunner() {
        return (args) -> {
            transactionalWorker.init();
            transactionalWorker.run();
        };
    }

    @Bean
    public ApplicationRunner threadRunner() {
        return (args) -> {
            long startTime = System.currentTimeMillis();
            for(int i = 0; i < THREAD_SIZE; i++) {
                new Thread(applicationContext.getBean(MybatisWorker.class), "thread-" + i).start();
            }
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    DruidApplication.stop = true;
                }
            }));
            countDownLatch.await();
            stop = true;
            System.out.println("运行总时间:" + (System.currentTimeMillis() - startTime) / 1000 + "/s");
        };
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public static abstract class SateWorker implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(SateWorker.class);

        private String threadName;

        private long maxWaitTime;

        private long countWaitTime;

        private int maxWaitTimeRefresh;

        private int errorIndex;

        private int index = 1;

        @Override
        public final void run() {
            threadName = Thread.currentThread().getName();
            while(!DruidApplication.stop && SUM_INDEX.get() < SUM_MAX) {
                long start = System.currentTimeMillis();
                try {
                    process();
                    SUM_INDEX.incrementAndGet();
                } catch (Exception e) {
                    errorIndex++;
                    LOGGER.error("thread name :{}, case:{}", threadName, e.getMessage(), e);
                }
                long currentWaitTime = System.currentTimeMillis() - start;
                if(currentWaitTime > maxWaitTime) {
                    maxWaitTime = currentWaitTime;
                    maxWaitTimeRefresh++;
                }
                countWaitTime += currentWaitTime;
                LOGGER.info("current wait:{},max wait:{},avg wait:{},index:{},wait time refresh:{},error count:{},thred name:{}",
                        currentWaitTime, maxWaitTime, countWaitTime / index, index++, maxWaitTimeRefresh, errorIndex, threadName);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
            countDownLatch.countDown();
        }


        protected abstract void process() throws Exception;
    }

    @Component
    @Scope("prototype")
    public static class noTransactionalWorker extends SateWorker {

        @Autowired
        private TransactionalService transactionalService;

        @Override
        protected void process() throws Exception {
            transactionalService.noTransactionSelect();
        }
    }

    @Component
    @Scope("prototype")
    public static class SupportWorker extends SateWorker {

        @Autowired
        private TransactionalService transactionalService;

        @Override
        protected void process() throws Exception {
            transactionalService.supportsSelect();
        }
    }

    @Component
    @Scope("prototype")
    public static class RequiredWorker extends SateWorker {

        @Autowired
        private TransactionalService transactionalService;

        @Override
        protected void process() throws Exception {
            transactionalService.requiredSelect();
        }
    }

    @Component
    @Scope("prototype")
    public static class DirectWorker extends SateWorker {

        @Autowired
        private DataSource dataSource;

        public DirectWorker(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        protected void process() throws Exception {
            dataSource.getConnection().close();

        }
    }

    @Component
    @Scope("prototype")
    public static class MybatisWorker extends SateWorker {

        @Autowired
        protected SelectOne selectOne;

        @Override
        protected void process() throws Exception {
            selectOne.select5s();
            selectOne.select2s();
        }
    }

    @Component
    @Scope("prototype")
    public static class MybatisTransactionalWorker extends SateWorker {

        @Autowired
        protected SelectOne selectOne;

        @Override
        @Transactional
        protected void process() throws Exception {
            selectOne.select2s();
            selectOne.select2s();
        }
    }

}
