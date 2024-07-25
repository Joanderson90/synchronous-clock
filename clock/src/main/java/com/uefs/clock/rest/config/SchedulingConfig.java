package com.uefs.clock.rest.config;

import com.uefs.clock.data.DataClock;
import com.uefs.clock.rest.service.clock.ClockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

    @Autowired
    private ClockService clockService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(aTaskScheduler());

        taskRegistrar.addTriggerTask(
                updateTime(), new ExponentialTrigger()
        );
    }

    static class ExponentialTrigger implements Trigger {

        @Override
        public Instant nextExecution(TriggerContext triggerContext) {
            Date lastDate = triggerContext.lastScheduledExecutionTime();

            long last;
            if (lastDate == null) {
                last = System.currentTimeMillis();
            } else {
                last = lastDate.getTime();
            }

            return new Date(last + DataClock.getCurrentDrift().intValue() * 1000L).toInstant();
        }


    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService aTaskScheduler() {
        return Executors.newScheduledThreadPool(1);
    }

    @Bean
    public Runnable updateTime() {
        return () -> {
            log.info("Rotate Clock: {}", clockService.getClockNameByClockCode(clockService.getThisClock().getCode()));

            log.info("Current time:{}s; Current drift: {}s; Is master: {}",
                    DataClock.getCurrentTime(),
                    DataClock.getCurrentDrift(),
                    DataClock.isMaster());

            DataClock.updateTime();
        };
    }


}
