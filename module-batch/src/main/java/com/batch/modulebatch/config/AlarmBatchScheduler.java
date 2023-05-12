package com.batch.modulebatch.config;

import com.batch.modulebatch.controller.AutoAlarmJobLauncherController;
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
public class AlarmBatchScheduler {

    @Qualifier
    private final Job autoAlarmJob;

    private final AutoAlarmJobLauncherController autoAlarmJobLauncherController;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void runBatchJob() throws Exception {
        autoAlarmJobLauncherController.startJob(autoAlarmJob);
    }
}
