package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoRes;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import com.simpletripbe.moduledomain.mycarrier.entity.TicketMemo;
import com.simpletripbe.moduledomain.mycarrier.mapper.TicketMemoMapper;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.TicketMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainTicketService {

    private final MyCarrierRepository myCarrierRepository;
    private final TicketMemoRepository ticketMemoRepository;
    private final TicketMemoMapper ticketMemoMapper;

    public void insertTicketMemo(String email, TicketMemoDTO ticketMemoDTO) {

        // 내용과 이미지 모두 null인 경우 예외처리
        if (ticketMemoDTO.getContent() == null && ticketMemoDTO.getImageUrl() == null) {
            throw new CustomException(CommonCode.EMPTY_CONTENT);
        }

        MyCarrier myCarrier = checkValidCarrierId(email, ticketMemoDTO.getCarrierId());

        Ticket ticket = checkValidTicketId(myCarrier, ticketMemoDTO.getTicketId());

        ticketMemoDTO.setMapper(ticket);

        TicketMemo ticketMemo = ticketMemoMapper.toTicketMemoEntity(ticketMemoDTO);

        ticketMemoRepository.save(ticketMemo);

    }

    public TicketMemoRes selectTicketMemo(String email, Long carrierId, Long ticketId) {

        MyCarrier myCarrier = checkValidCarrierId(email, carrierId);

        checkValidTicketId(myCarrier, ticketId);

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketId);

        if (ticketMemoOptional.isPresent()) {
            return ticketMemoMapper.toTicketMemoRes(ticketMemoOptional.get());
        } else {
            return new TicketMemoRes();
        }

    }


    public void updateTicketMemo(String email, TicketMemoDTO ticketMemoDTO) {

    }

    public void deleteTicketMemo(String email, Long carrierId, Long ticketId) {

    }

    /**
     * 전달받은 캐리어 ID가 올바른지 확인하는 메서드
     */
    private MyCarrier checkValidCarrierId(String email, Long carrierId) {

        Optional<MyCarrier> myCarrierOptional = myCarrierRepository.findById(carrierId);

        // 존재하지 않는 캐리어 예외처리
        if (myCarrierOptional.isEmpty()) {
            throw new CustomException(CommonCode.NONEXISTENT_CARRIER);
        }

        MyCarrier myCarrier = myCarrierOptional.get();

        // 사용자의 캐리어가 아닌 경우 예외처리
        if (!myCarrier.getUser().getEmail().equals(email)) {
            throw new CustomException(CommonCode.INVALID_CARRIER_ACCESS);
        }

        return myCarrier;
    }

    /**
     * 전달받은 티켓이 캐리어에 존재하는지 확인하고 일치하는 Ticket 객체 리턴하는 메서드
     */
    private Ticket checkValidTicketId(MyCarrier myCarrier, Long ticketId) {

        List<Ticket> tickets = myCarrier.getTickets();

        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(ticketId)) {
                return ticket;
            }
        }

        throw new CustomException(CommonCode.NONEXISTENT_TICKET);
    }

}
