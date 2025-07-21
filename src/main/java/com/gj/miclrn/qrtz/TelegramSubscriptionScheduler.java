package com.gj.miclrn.qrtz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gj.miclrn.messangers.TelegramConstants;

@Service
public class TelegramSubscriptionScheduler {

	@Autowired
	private Scheduler scheduler;

	public void scheduleSubscription(String chatId, String topic, String cronExpression) throws SchedulerException {
		JobDetail jobDetail = buildJobDetail(chatId, topic);
		Trigger trigger = buildJobTrigger(jobDetail, cronExpression);
		scheduler.scheduleJob(jobDetail, trigger);
	}

	public void unscheduleSubscription(String chatId, String topic) throws SchedulerException {
		JobKey jobKey = new JobKey("job_" + chatId + "_" + topic, "telegram_subscriptions");
		scheduler.deleteJob(jobKey);
	}

	private JobDetail buildJobDetail(String chatId, String topic) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(TelegramConstants.TELEGRAM_CHAT_ID, chatId);
		jobDataMap.put(TelegramConstants.TELEGRAM_MESSAGE, topic);

		return JobBuilder.newJob(TelegramUpdatesJob.class)
				.withIdentity("job_" + chatId + "_" + topic, "telegram_subscriptions")
				.withDescription("Telegram Notification Job").usingJobData(jobDataMap).storeDurably().build();
	}

	private Trigger buildJobTrigger(JobDetail jobDetail, String cronExpression) {
		return TriggerBuilder.newTrigger().forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName() + "_trigger", "telegram_subscriptions")
				.withDescription("Telegram Notification Trigger")
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	}
}
