package com.hari.tamil_movies.Schedule;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobConfig {
//
//
//        public JobDetail jobDetail(){
//            return JobBuilder.newJob(ScheduleService.class).build();
//        }
//

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(ScheduleService.class)
                .withIdentity("tamilMovieJob")
                .storeDurably()
                .build();
    }



    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("tamilMovieTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();
    }










}
