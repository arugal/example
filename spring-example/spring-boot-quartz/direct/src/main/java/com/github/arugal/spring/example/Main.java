package com.github.arugal.spring.example;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zhangwei
 */
@Slf4j
public class Main {

	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory fac = new StdSchedulerFactory();
		Scheduler scheduler = fac.getScheduler();

		JobDetail jobDetail =
			JobBuilder.newJob(ExampleJob.class).withIdentity("job", "1").build();

		CronTrigger cron =
			TriggerBuilder.newTrigger().withIdentity("cron", "1").withSchedule(CronScheduleBuilder.cronSchedule("10 * * * * ?")).build();

		scheduler.scheduleJob(jobDetail, cron);
		scheduler.start();
	}

	@Slf4j
	public static class ExampleJob implements Job {

		@Override
		public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
			log.info("previous fire time: {}", jobExecutionContext.getPreviousFireTime());
			log.info("next fire time: {}", jobExecutionContext.getNextFireTime());
		}
	}
}
