package com.batch.modulebatch.job.smsalarm;

import com.batch.modulebatch.config.BatchConfig;
import com.batch.modulebatch.controller.JobCompletionDecider;
import com.simpletripbe.moduledomain.batch.api.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class AutoAlarmJobConfig {

    static final String JOB_NAME = "auto-alarm-save";
    private static final String STEP_NAME = JOB_NAME + "-step";
    private final JobBuilderFactory jobBuilderFactory;
    private final JobRepository jobRepository;
    private final StepBuilderFactory stepBuilderFactory;

//    @Primary
    @Bean("autoAlarmJob")
    public Job autoAlarmJob(
            AutoAlarmTasklet autoAlarmTasklet,
            JobCompletionDecider jobCompletionDecider
    ) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("autoAlarmFlow");
        Flow flow = flowBuilder
                .start(autoAlarmStep(autoAlarmTasklet))
                .on("COMPLETED").end()
                .build();
        return jobBuilderFactory.get(JOB_NAME)
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(flow)
                .next(jobCompletionDecider).on("CONTINUE").to(flow)
                .end()
                .build();
    }

    @Bean
    public JobCompletionDecider jobCompletionDecider() {
        return new JobCompletionDecider();
    }

    @Bean("autoAlarmStep")
    public Step autoAlarmStep(
            AutoAlarmTasklet autoAlarmTasklet
    ) {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet(autoAlarmTasklet)
                .transactionManager(new ResourcelessTransactionManager())
                .build();
    }

}