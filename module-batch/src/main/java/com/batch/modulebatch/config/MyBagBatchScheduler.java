package com.batch.modulebatch.config;

import com.batch.modulebatch.controller.MyBagJobLauncherController;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class MyBagBatchScheduler {

    @Qualifier("myBagJob")
    private final Job myBagJob;

    private final MyBagJobLauncherController myBagJobLauncherController;

    @Scheduled(cron = "0 0 22 * * ?")
    public void runBatchJob() throws Exception {
        myBagJobLauncherController.startJob(myBagJob);
    }
}
