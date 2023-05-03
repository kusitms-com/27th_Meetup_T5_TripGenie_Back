package com.batch.modulebatch.job.mybag;

import com.simpletripbe.moduledomain.batch.api.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Slf4j
@RequiredArgsConstructor
public class MyBagTasklet implements Tasklet {

    private final BatchService batchService;

    @Value("#{jobParameters['startDate']}")
    private String startDate;
    @Value("#{jobParameters['endDate']}")
    private String endDate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        try {
            batchService.save(LocalDate.parse(endDate));
            return RepeatStatus.FINISHED;
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("날짜 형식이 올바르지 않습니다.(YYYY-MM-DD)");
        }

    }

}
