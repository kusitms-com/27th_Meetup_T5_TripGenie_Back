package com.batch.modulebatch.job.mybag;

import com.simpletripbe.moduledomain.batch.api.BatchService;
import com.simpletripbe.moduledomain.batch.dto.MyBagSaveDTO;
import com.simpletripbe.moduledomain.batch.dto.MyBagTicketDTO;
import com.simpletripbe.moduledomain.mycarrier.api.MainCarrierService;
import com.simpletripbe.moduledomain.mycarrier.entity.CarrierType;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@JobScope
public class MyBagTasklet implements Tasklet {

    private final BatchService batchService;
    private final MainCarrierService mainCarrierService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        try {

            List<MyBagTicketDTO> ticketList = mainCarrierService.selectTicketList();
//            for (int i = 0; i < ticketList.size(); i++) {
//
//                if (LocalDate.now().isEqual(ticketList.get(i).getEndDate().plusDays(1))) {
//
//                    MyBagSaveDTO dto = new MyBagSaveDTO();
//                    dto.setType(CarrierType.STORAGE);
//                    dto.setUpdDate(LocalDateTime.now());
//                    batchService.saveMyBag(dto);
//                }
//            }

            for (MyBagTicketDTO ticket : ticketList) {
                if (LocalDate.now().isEqual(ticket.getEndDate().plusDays(1))) {
//                    MyBagSaveDTO myBagSaveDTO = new MyBagSaveDTO(ticket.getId(), CarrierType.STORAGE, LocalDateTime.now());
//                    log.info("ticketId : " + myBagSaveDTO.getTicketId());
//                    batchService.saveMyBag(myBagSaveDTO);
                    Ticket findTicket = mainCarrierService.findTicketById(ticket.getId());
                    MyCarrier findCarrier = mainCarrierService.findMyCarrierById(findTicket.getMyCarrier().getId());
                    findCarrier.updateType(CarrierType.STORAGE);
                }
            }

            return RepeatStatus.FINISHED;
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("날짜 형식이 올바르지 않습니다.(YYYY-MM-DD)");
        }

    }

}
