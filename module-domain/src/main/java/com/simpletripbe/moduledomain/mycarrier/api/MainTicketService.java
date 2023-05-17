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
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainTicketService {

    @Value("${cloud.aws.s3.url}")
    private String s3Url;
    private final MyCarrierRepository myCarrierRepository;
    private final TicketMemoRepository ticketMemoRepository;
    private final TicketMemoMapper ticketMemoMapper;
    private final AwsS3Service awsS3Service;


    @Transactional
    public TicketMemoRes insertTicketMemo(String email, TicketMemoDTO ticketMemoDTO, MultipartFile multipartFile) throws FileUploadException {

        // 내용과 이미지 모두 null인 경우 예외처리
        if (ticketMemoDTO.getContent() == null && multipartFile.isEmpty()) {
            throw new CustomException(CommonCode.EMPTY_CONTENT);
        }

        MyCarrier myCarrier = checkValidCarrierId(email, ticketMemoDTO.getCarrierId());

        Ticket ticket = checkValidTicketId(myCarrier, ticketMemoDTO.getTicketId());

        if (multipartFile.isEmpty()) {
            ticketMemoDTO.setMapper(ticket, null);
        } else {
            String url = awsS3Service.uploadFile(multipartFile);
            ticketMemoDTO.setMapper(ticket, url);
        }

        TicketMemo ticketMemo = ticketMemoMapper.toTicketMemoEntity(ticketMemoDTO);

        ticketMemoRepository.save(ticketMemo);

        return new TicketMemoRes(ticketMemo.getImageUrl());

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

    @Transactional
    public TicketMemoRes updateTicketMemo(String email, TicketMemoDTO ticketMemoDTO, MultipartFile multipartFile) throws FileUploadException {

        String url = null;

        // 내용과 이미지 모두 null인 경우 예외처리
        if (ticketMemoDTO.getContent() == null && multipartFile.isEmpty()) {
            throw new CustomException(CommonCode.EMPTY_CONTENT);
        }

        MyCarrier myCarrier = checkValidCarrierId(email, ticketMemoDTO.getCarrierId());

        Ticket ticket = checkValidTicketId(myCarrier, ticketMemoDTO.getTicketId());

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketMemoDTO.getTicketId());

        // 티켓 메모가 존재하지 않는 경우 예외처리
        if (ticketMemoOptional.isEmpty()) {
            throw new CustomException(CommonCode.NONEXISTENT_TICKET_MEMO);
        }

        TicketMemo ticketMemo = ticketMemoOptional.get();
        // 티켓 메모의 이미지가 존재하는 경우에
        if (ticketMemo.getImageUrl() != null) {
            // 이미지를 수정한다면
            if (!multipartFile.isEmpty()) {
                // 기존 이미지 삭제하고
                awsS3Service.deleteFile(ticketMemo.getImageUrl().replace(s3Url, ""));
                // 새로운 이미지 업로드
                url = awsS3Service.uploadFile(multipartFile);
            } else {
                url = ticketMemo.getImageUrl();
            }
        }

        ticketMemoDTO.setMapper(ticket, url, ticketMemo.getId());

        ticketMemoRepository.updateTicketMemo(ticketMemoDTO);

        return new TicketMemoRes(ticketMemo.getImageUrl());

    }

    @Transactional
    public void deleteTicketMemo(String email, Long carrierId, Long ticketId) {

        MyCarrier myCarrier = checkValidCarrierId(email, carrierId);

        checkValidTicketId(myCarrier, ticketId);

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketId);

        if (ticketMemoOptional.isEmpty()) {
            throw new CustomException(CommonCode.NONEXISTENT_TICKET_MEMO);
        }

        TicketMemo ticketMemo = ticketMemoOptional.get();

        // 이미지가 존재하는 경우 S3 이미지 삭제
        if (ticketMemo.getImageUrl() != null) {
            awsS3Service.deleteFile(ticketMemoOptional.get().getImageUrl().replace(s3Url, ""));
        }

        ticketMemoRepository.delete(ticketMemo);

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
