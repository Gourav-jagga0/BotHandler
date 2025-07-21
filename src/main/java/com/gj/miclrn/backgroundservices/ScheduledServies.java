package com.gj.miclrn.backgroundservices;

import com.gj.miclrn.backgroundservices.scheduledServices.ScheduleConfig;

public interface ScheduledServies extends Runnable {
	ScheduleConfig getSchedule();
	boolean shouldSchedule();

}
