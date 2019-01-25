package com.github.sunnus3.example.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author: zhangwei
 * @date: 15:59/2019-01-22
 */
public abstract class AbstractApplication implements CommandLineRunner, ApplicationContextAware {

    protected final static Logger logger = LoggerFactory.getLogger(AbstractApplication.class);

    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        logger.info("ApplicationContext:"+applicationContext.getClass().getName());

    }

    @Override
    public void run(String... args) throws Exception {
        UserService service = applicationContext.getBean(UserServiceImpl.class);
        service.save(null);
    }
}
