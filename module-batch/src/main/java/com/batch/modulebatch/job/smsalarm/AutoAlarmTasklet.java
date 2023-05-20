package com.batch.modulebatch.job.smsalarm;

import com.simpletripbe.moduledomain.batch.api.BatchService;
import com.simpletripbe.moduledomain.batch.dto.AlarmSendDTO;
import com.simpletripbe.moduledomain.batch.dto.TicketListDTO;
import com.simpletripbe.moduledomain.mycarrier.api.MainCarrierService;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@JobScope
public class AutoAlarmTasklet implements Tasklet {

    private final BatchService batchService;
    private final MainCarrierService mainCarrierService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<TicketListDTO> ticketList = (List<TicketListDTO>) chunkContext.getStepContext().getStepExecution().getJobExecution()
                .getExecutionContext().get("ticketList");
        boolean isAlarmCreated = false;

        if (ticketList == null) {
            ticketList = mainCarrierService.selectCarrierList();
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext()
                    .put("ticketList", ticketList);
        }

        for (int i = 0; i < ticketList.size(); i++) {
            if (LocalDate.now().isEqual(ticketList.get(i).getStartDate().minusDays(1))) {
                AlarmSendDTO dto = new AlarmSendDTO();
                dto.setMessage(ticketList.get(0).getUser().getNickname() + "님, 오늘은 " + ticketList.get(0).getName() + " 가는 날 입니다! 티켓을 잘 모았는지 확인해보세요!");
                dto.setName(ticketList.get(i).getName());
                dto.setStartDate(ticketList.get(i).getStartDate());
                dto.setEndDate(ticketList.get(i).getEndDate());
                dto.setEmail(ticketList.get(i).getUser());

                batchService.saveStartAlarm(dto);
                isAlarmCreated = true;
            } else if (LocalDate.now().isEqual(ticketList.get(i).getEndDate().plusDays(1))) {
                AlarmSendDTO dto = new AlarmSendDTO();
                dto.setMessage(ticketList.get(0).getUser().getNickname() + "님, " + ticketList.get(0).getName() + " 즐거우셨나요? 보관함에서 회고를 진행해보세요!");
                dto.setName(ticketList.get(i).getName());
                dto.setStartDate(ticketList.get(i).getStartDate());
                dto.setEndDate(ticketList.get(i).getEndDate());
                dto.setEmail(ticketList.get(i).getUser());

                batchService.saveEndAlarm(dto);
                isAlarmCreated = true;
            }
        }

        if (isAlarmCreated) {
            return RepeatStatus.FINISHED;
        } else {
            return RepeatStatus.CONTINUABLE;
        }
    }

}