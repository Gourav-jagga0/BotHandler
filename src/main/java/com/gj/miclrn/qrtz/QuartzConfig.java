package com.gj.miclrn.qrtz;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {
	
	  @Autowired
	    private ApplicationContext applicationContext;
    
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        // Pass Spring context to Quartz
        Map<String, Object> schedulerContext = new HashMap<>();
        schedulerContext.put("applicationContext", applicationContext);
        schedulerFactory.setSchedulerContextAsMap(schedulerContext);
        return schedulerFactory;
    }
}