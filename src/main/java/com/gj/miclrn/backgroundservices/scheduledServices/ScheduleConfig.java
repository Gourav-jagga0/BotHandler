package com.gj.miclrn.backgroundservices.scheduledServices;

import java.util.concurrent.TimeUnit;

public class ScheduleConfig {

    private final long interval;
    private final TimeUnit timeUnit;

    public ScheduleConfig(long interval, TimeUnit timeUnit) {
        if (interval <= 0) {
            throw new IllegalArgumentException("Interval must be > 0");
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("TimeUnit must not be null");
        }
        this.interval = interval;
        this.timeUnit = timeUnit;
    }

    public long getInterval() {
        return interval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

	@Override
	public String toString() {
		return "ScheduleConfig [interval=" + interval + ", timeUnit=" + timeUnit + "]";
	}
    
}
