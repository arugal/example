package com.github.arugal.spring.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zhangwei
 */
public class Main {

	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory fac = new StdSchedulerFactory();
		Scheduler scheduler = fac.getScheduler();

		JobDetail jobDetail =
			JobBuilder.newJob(ExampleJob.class).withIdentity("example-job", "1").build();

		CronTrigger trigger =
			TriggerBuilder.newTrigger().withIdentity("example-trigger", "1").withSchedule(CronScheduleBuilder.cronSchedule("10 * * * * ?")).build();

		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}


	public static class ExampleJob implements Job {

		@Override
		public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
			System.out.println("execute example job next fire time:" + jobExecutionContext.getNextFireTime());
		}
	}
}
