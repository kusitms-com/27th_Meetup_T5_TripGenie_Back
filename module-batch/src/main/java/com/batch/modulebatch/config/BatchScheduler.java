package com.batch.modulebatch.config;

import com.batch.modulebatch.controller.JobLauncherController;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    @Autowired
    private JobLauncherController jobLauncherController;

    @Autowired
    private Job myJob;

    @Scheduled(cron = "0 0 1 * * ?")
    public void runBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncherController.startJob(jobParameters);
    }
}
