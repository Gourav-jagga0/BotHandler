package com.gj.miclrn;

import java.util.Map;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.gj.miclrn.backgroundservices.BackgroundService;

@Component
public class Startup {

	@EventListener
	public void handleApplicationStarted(ApplicationStartedEvent event) {
		initializeBackgroundServices(event);
	}

	private void initializeBackgroundServices(ApplicationStartedEvent event) {
		Map<String, BackgroundService> backgroundBeans = event.getApplicationContext()
				.getBeansOfType(BackgroundService.class);

		if (backgroundBeans.isEmpty()) {
			System.out.println("No BackgroundService beans found.");
			return;
		}

		backgroundBeans.forEach((beanName, service) -> {
			System.out.println("Initializing background bean: " + beanName);
			service.init(event.getApplicationContext());
		});
	}
}
