package com.gj.miclrn.qrtz;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.gj.miclrn.messanger.MessageService;
import com.gj.miclrn.messangers.TelegramConstants;

public class TelegramUpdatesJob implements Job {
	@Override
	public void execute(JobExecutionContext context) {
		try {
			ApplicationContext appContext = (ApplicationContext) context.getScheduler().getContext()
					.get("applicationContext");
			MessageService messageService = (MessageService) appContext.getBean("telegramMessanger");
			Map data = context.getJobDetail().getJobDataMap();
			messageService.sendMessage((String) data.get(TelegramConstants.TELEGRAM_MESSAGE),
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, data.get(TelegramConstants.TELEGRAM_CHAT_ID),
							TelegramConstants.TELEGRAM_MESSAGE, data.get(TelegramConstants.TELEGRAM_MESSAGE)));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}
}
