package com.batch.modulebatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("job")
public class JobLauncherController {

    private final JobLauncher jobLauncher;

    @Qualifier("myBagJob")
    private final Job myBagJob;

    @Qualifier("autoAlarmJob")
    private final Job autoAlarmJob;

    @PostMapping("/batch/start")
    public String startJob(@RequestBody JobParameters jobParameters) throws Exception {

        JobExecution myBagJobExecution = jobLauncher.run(myBagJob, jobParameters);
        JobExecution autoAlarmJobExecution = jobLauncher.run(autoAlarmJob, jobParameters);

        if (myBagJobExecution.getStatus() == BatchStatus.COMPLETED) {
            // Job 실행이 성공한 경우 처리할 내용
            System.out.println("myBagJob executed successfully.");
        } else {
            // Job 실행이 실패한 경우 처리할 내용
            System.out.println("myBagJob failed with status: " + myBagJobExecution.getStatus());
            System.out.println("Exit description: " + myBagJobExecution.getExitStatus().getExitDescription());
        }

        if (autoAlarmJobExecution.getStatus() == BatchStatus.COMPLETED) {
            // Job 실행이 성공한 경우 처리할 내용
            System.out.println("autoAlarmJob executed successfully.");
        } else {
            // Job 실행이 실패한 경우 처리할 내용
            System.out.println("autoAlarmJob failed with status: " + autoAlarmJobExecution.getStatus());
            System.out.println("Exit description: " + autoAlarmJobExecution.getExitStatus().getExitDescription());
        }

        return "Batch Job has been invoked";
    }
}

