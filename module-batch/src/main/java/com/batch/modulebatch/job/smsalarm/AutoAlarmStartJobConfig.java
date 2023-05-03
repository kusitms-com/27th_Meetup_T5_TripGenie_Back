package com.batch.modulebatch.job.smsalarm;

import com.batch.modulebatch.config.BatchConfig;
import com.simpletripbe.moduledomain.batch.api.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@ConditionalOnProperty(
        value = BatchConfig.SPRING_BATCH_JOB_NAMES,
        havingValue = AutoAlarmStartJobConfig.JOB_NAME
)
@Configuration
@RequiredArgsConstructor
public class AutoAlarmStartJobConfig {

    static final String JOB_NAME = "auto-alarm-save";
    private static final String STEP_NAME = JOB_NAME + "-step";

    private final JobBuilderFactory jobBuilderFactory;
    private final JobRepository jobRepository;
    private final StepBuilderFactory stepBuilderFactory;
    private final BatchService batchService;

    @Bean
    @Scheduled(cron = "0 0 1 * * *")
    public Job saveAutoAlarmJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .repository(jobRepository)
                .start(saveAutoAlarmStep())
                .build();
    }

    @Bean
    @JobScope
    public Step saveAutoAlarmStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet(AutoAlarmTasklet())
                .transactionManager(new ResourcelessTransactionManager())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet AutoAlarmTasklet() {
        return new AutoAlarmStartTasklet(batchService);
    }

}
