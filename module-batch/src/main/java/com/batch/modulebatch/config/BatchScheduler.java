package com.batch.modulebatch.config;

import com.batch.modulebatch.controller.JobLauncherController;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncherController jobLauncherController;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void runBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncherController.startJob(jobParameters);
    }
}
