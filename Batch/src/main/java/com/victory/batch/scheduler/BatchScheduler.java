package com.victory.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(){
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Scheduled(cron = "0/10 * * * * *") // once every 10seconds
    @Qualifier("DeviceInfoSync")
    public void runDeviceInfoSyncJob(){
        String time = LocalDateTime.now().toString();
        try{
            Job job = jobRegistry.getJob("DeviceInfoSync");
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder().addString("time",time);
            jobLauncher.run(job, jobParametersBuilder.toJobParameters());
        } catch (
                JobInstanceAlreadyCompleteException |
                NoSuchJobException |
                JobExecutionAlreadyRunningException |
                JobParametersInvalidException |
                JobRestartException e
        ) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0/5 * * * * *") // once every 10seconds
    @Qualifier("DeviceInfoSync2")
    public void runDeviceInfoSyncJob2(){
        String time = LocalDateTime.now().toString();
        try{
            Job job = jobRegistry.getJob("DeviceInfoSync2");
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder().addString("time",time);
            jobLauncher.run(job, jobParametersBuilder.toJobParameters());
        } catch (
                JobInstanceAlreadyCompleteException |
                NoSuchJobException |
                JobExecutionAlreadyRunningException |
                JobParametersInvalidException |
                JobRestartException e
        ) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }
}
