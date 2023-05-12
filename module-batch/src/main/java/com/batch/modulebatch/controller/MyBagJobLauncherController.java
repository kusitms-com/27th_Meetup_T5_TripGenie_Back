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
@RequestMapping("myBagJob")
public class MyBagJobLauncherController {

    private final JobLauncher jobLauncher;

    @PostMapping("/batch/start")
    public String startJob(Job myBagJob) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution myBagJobExecution = jobLauncher.run(myBagJob, jobParameters);

        if (myBagJobExecution.getStatus() == BatchStatus.COMPLETED) {
            // Job 실행이 성공한 경우 처리할 내용
            System.out.println("myBagJob executed successfully.");
        } else {
            // Job 실행이 실패한 경우 처리할 내용
            System.out.println("myBagJob failed with status: " + myBagJobExecution.getStatus());
            System.out.println("Exit description: " + myBagJobExecution.getExitStatus().getExitDescription());
        }

        return "My Bag Batch Job has been invoked";
    }

}

