package com.github.arugal.spring.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.quartz.CronExpression;

import java.text.ParseException;

/**
 * @author zhangwei
 */
@Slf4j
public class CronTest {

	@Test
	public void cronExpression() throws ParseException {
		CronExpression expression = new CronExpression("10 * * * * ?");

		log.info(expression.toString());
	}
}
