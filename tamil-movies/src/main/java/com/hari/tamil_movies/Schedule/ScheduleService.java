package com.hari.tamil_movies.Schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ScheduleService implements InitializingBean, Job {

    @Autowired
    private TaskScheduler taskScheduler;


//    @Scheduled(fixedRate = 5000)
    public void test() {
        System.out.println("testing the scheduler....");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
//        taskScheduler.scheduleAtFixedRate(this::test, Duration.ofSeconds(5));

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        test();

    }
}
