package com.batch.modulebatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("autoAlarmJob")
public class AutoAlarmJobLauncherController {

    private final JobLauncher jobLauncher;

    @PostMapping("/batch/start")
    public String startJob(Job autoAlarmJob) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution autoAlarmJobExecution = jobLauncher.run(autoAlarmJob, jobParameters);

        if (autoAlarmJobExecution.getStatus() == BatchStatus.COMPLETED) {
            // Job 실행이 성공한 경우 처리할 내용
            System.out.println("autoAlarmJob executed successfully.");
        } else {
            // Job 실행이 실패한 경우 처리할 내용
            System.out.println("autoAlarmJob failed with status: " + autoAlarmJobExecution.getStatus());
            System.out.println("Exit description: " + autoAlarmJobExecution.getExitStatus().getExitDescription());
        }

        return "Auto Alarm Batch Job has been invoked";
    }

}

