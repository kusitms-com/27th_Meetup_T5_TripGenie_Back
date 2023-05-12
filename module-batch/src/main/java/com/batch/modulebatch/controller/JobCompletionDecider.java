package com.batch.modulebatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;

public class JobCompletionDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            return FlowExecutionStatus.COMPLETED;
        } else {
            return FlowExecutionStatus.FAILED;
        }
    }
}