package com.victory.batch.configuration;

import com.victory.batch.service.DeviceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
public class DeviceInfoJobConfiguration extends DefaultBatchConfiguration {

    @Autowired
    DeviceInfoService deviceInfoService;

    @Bean(name="DeivceInfoSync")
    public Job deviceInfoSyncJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new JobBuilder("DeviceInfoSync",jobRepository)
                .start(deviceInfoSyncStep(jobRepository,platformTransactionManager))
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean(name="DeivceInfoSyncStep")
    public Step deviceInfoSyncStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("DeivceInfoSyncStep",jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    deviceInfoService.deviceInfoCaching();
                    return RepeatStatus.FINISHED;
                },platformTransactionManager)
                .build();
    }

    @Bean(name="DeivceInfoSync2")
    public Job deviceInfoSyncJob2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new JobBuilder("DeviceInfoSync2",jobRepository)
                .start(deviceInfoSyncStep2(jobRepository,platformTransactionManager))
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean(name="DeivceInfoSyncStep2")
    public Step deviceInfoSyncStep2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("DeivceInfoSyncStep2",jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    //deviceInfoService.deviceInfoCaching();
                    return RepeatStatus.FINISHED;
                },platformTransactionManager)
                .build();
    }
}
