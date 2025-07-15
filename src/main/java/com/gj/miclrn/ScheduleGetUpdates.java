package com.gj.miclrn;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleGetUpdates {
    public static void init() {
        ScheduledExecutorService checkUpdates = Executors.newScheduledThreadPool(1);
        checkUpdates.scheduleWithFixedDelay(new checkUpdates(), 0, 15, TimeUnit.MINUTES);
    }

}

class checkUpdates implements Runnable {
    static Logger log = LoggerFactory.getLogger(checkUpdates.class);

    @Override
    public void run() {
        log.info("checking for the updated");
    }

}