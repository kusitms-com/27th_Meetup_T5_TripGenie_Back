package com.batch.modulebatch.config;

import com.batch.modulebatch.scheduler.AutoAlarmJobScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class AlarmBatchScheduler {

    @Qualifier("myBagJob")
    private final Job myBagJob;

    @Qualifier("autoAlarmJob")
    private final Job autoAlarmJob;

    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 5 23 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void runBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobName", "myBagJob" + System.currentTimeMillis())
                .toJobParameters();

        JobExecution execution1 = jobLauncher.run(myBagJob, jobParameters);
        while (execution1.isRunning()) {
            // 대기
        }
    }
}