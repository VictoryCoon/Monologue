package com.victory.batch.configuration;

import com.victory.batch.service.DeviceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
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
public class BatchConfiguration extends DefaultBatchConfiguration {

    @Autowired
    DeviceInfoService deviceInfoService;

    @Bean
    public Job job(JobRepository jobRepository, Step step){
        return new JobBuilder("DeviceInfoSync",jobRepository).start(step).build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        Step step = new StepBuilder("DeviceInfoSyncStep",jobRepository).tasklet(tasklet(),platformTransactionManager).build();
        return step;
    }

    public Tasklet tasklet(){
        return ((contribution, chunkContext) -> {
            log.info("====================== BATCH TASKLET ======================");
            // TODO : 여기에 Service
            deviceInfoService.deviceInfoCaching();
            return RepeatStatus.FINISHED;
        });
    }
}
