package com.gj.miclrn.backgroundservices.scheduledServices;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.gj.miclrn.backgroundservices.BackgroundService;
import com.gj.miclrn.backgroundservices.ScheduledServies;

@Component
public class ScheduledServicesImpl implements BackgroundService {
	private static Logger log = LoggerFactory.getLogger(ScheduledServicesImpl.class);

	@Override
	public void init(ApplicationContext context) {
		Map<String, ScheduledServies> scheduledServies = context.getBeansOfType(ScheduledServies.class);
		if (scheduledServies.isEmpty()) {
			log.info("No Scheduled Services found.");
			return;
		}

		scheduledServies.forEach((beanName, service) -> {
			if (!service.shouldSchedule()) {
				log.info(" Scheduling Service {}  is disabled ", service.getClass().getCanonicalName());
				return;
			}
			ScheduleConfig SC = service.getSchedule();
			log.info(" Scheduling Service {} with Schedule {}", service.getClass().getCanonicalName(), SC);
			ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
			scheduledExecutorService.scheduleWithFixedDelay(service, 0, SC.getInterval(), SC.getTimeUnit());
		});
	}

}