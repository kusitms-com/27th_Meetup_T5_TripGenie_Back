package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoRes;
import com.simpletripbe.moduledomain.mycarrier.entity.CarrierType;
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
    private String S3_URL;
    private final UserRepository userRepository;
    private final MyCarrierRepository myCarrierRepository;
    private final TicketMemoRepository ticketMemoRepository;
    private final TicketMemoMapper ticketMemoMapper;
    private final AwsS3Service awsS3Service;


    @Transactional
    public TicketMemoRes insertTicketMemo(String email, TicketMemoDTO ticketMemoDTO, MultipartFile multipartFile) throws FileUploadException {

        checkExistImageOrContent(multipartFile, ticketMemoDTO.getContent());

        MyCarrier myCarrier = checkValidCarrierId(email, ticketMemoDTO.getCarrierId());

        checkCarrierType(myCarrier);

        Ticket ticket = checkValidTicketId(myCarrier, ticketMemoDTO.getTicketId());

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticket.getId());

        checkNotExistTicketMemo(ticketMemoOptional);

        // 이미지가 존재하지 않는 경우
        if (multipartFile == null) {
            ticketMemoDTO.setMapper(ticket, null);
        } else { // 이미지가 존재하는 경우
            String url = awsS3Service.uploadFile(multipartFile);
            ticketMemoDTO.setMapper(ticket, url);
        }

        TicketMemo ticketMemo = ticketMemoMapper.toTicketMemoEntity(ticketMemoDTO);

        // 글자수가 100자 이상이라면 캐시 지급
        giveGenieCash(email, ticketMemoDTO.getContent());

        ticketMemoRepository.save(ticketMemo);

        return new TicketMemoRes(ticketMemo.getImageUrl());

    }

    @Transactional
    public TicketMemoRes selectTicketMemo(String email, Long carrierId, Long ticketId) {

        MyCarrier myCarrier = checkValidCarrierId(email, carrierId);

        checkCarrierType(myCarrier);

        checkValidTicketId(myCarrier, ticketId);

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketId);

        if (ticketMemoOptional.isPresent()) {
            return ticketMemoMapper.toTicketMemoRes(ticketMemoOptional.get());
        } else {
            throw new CustomException(CommonCode.NONEXISTENT_TICKET_MEMO);
        }

    }

    @Transactional
    public TicketMemoRes updateTicketMemo(String email, TicketMemoDTO ticketMemoDTO, MultipartFile multipartFile) throws FileUploadException {

        String url = null;

        checkExistImageOrContent(multipartFile, ticketMemoDTO.getContent());

        MyCarrier myCarrier = checkValidCarrierId(email, ticketMemoDTO.getCarrierId());

        checkCarrierType(myCarrier);

        Ticket ticket = checkValidTicketId(myCarrier, ticketMemoDTO.getTicketId());

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketMemoDTO.getTicketId());

        checkExistTicketMemo(ticketMemoOptional);

        TicketMemo ticketMemo = ticketMemoOptional.get();

        // 티켓 메모의 이미지를 수정하는 경우
        if (!(multipartFile == null)) {
            // 기존에 이미지가 존재했다면
            if (ticketMemo.getImageUrl() != null) {
                // 해당 이미지 삭제
                awsS3Service.deleteFile(ticketMemo.getImageUrl().replace(S3_URL, ""));
            }
            url = awsS3Service.uploadFile(multipartFile);
        } else {
            if (ticketMemo.getImageUrl() != null) {
                url = ticketMemo.getImageUrl();
            }
        }

        ticketMemoDTO.setMapper(ticket, url, ticketMemo.getId());
        ticketMemoRepository.updateTicketMemo(ticketMemoDTO);

        return new TicketMemoRes(url);

    }

    @Transactional
    public void deleteTicketMemo(String email, Long carrierId, Long ticketId) {

        MyCarrier myCarrier = checkValidCarrierId(email, carrierId);

        checkCarrierType(myCarrier);

        checkValidTicketId(myCarrier, ticketId);

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketId);

        checkExistTicketMemo(ticketMemoOptional);

        TicketMemo ticketMemo = ticketMemoOptional.get();

        // 이미지가 존재하는 경우 S3 이미지 삭제
        if (ticketMemo.getImageUrl() != null) {
            awsS3Service.deleteFile(ticketMemoOptional.get().getImageUrl().replace(S3_URL, ""));
        }

        ticketMemoRepository.deleteTicketMemo(ticketMemo.getId());

    }

    @Transactional
    public void checkExist(String email, Long carrierId, Long ticketId) {

        MyCarrier myCarrier = checkValidCarrierId(email, carrierId);

        checkCarrierType(myCarrier);

        checkValidTicketId(myCarrier, ticketId);

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketId);

        checkExistTicketMemo(ticketMemoOptional);

    }

    /**
     * 전달받은 캐리어 ID가 올바른지 확인하는 메서드
     */
    private MyCarrier checkValidCarrierId(String email, Long carrierId) {

        Optional<MyCarrier> myCarrierOptional = myCarrierRepository.findById(carrierId);

        // 존재하지 않는 캐리어 예외처리
        if (myCarrierOptional.isEmpty() || myCarrierOptional.get().getDeleteYn().equals("Y")) {
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
            if (ticket.getId().equals(ticketId) && ticket.getDeleteYn().equals("N")) {
                return ticket;
            }
        }

        throw new CustomException(CommonCode.NONEXISTENT_TICKET);
    }

    /**
     * 티켓 메모가 존재하는 경우 처리하는 메서드
     */
    private void checkNotExistTicketMemo(Optional<TicketMemo> ticketMemoOptional) {

        if (ticketMemoOptional.isPresent()) {
            throw new CustomException(CommonCode.TICKET_MEMO_ALREADY_EXIST);
        }
    }

    /**
     * 티켓 메모가 존재하지 않는 경우 처리하는 메서드
     */
    private void checkExistTicketMemo(Optional<TicketMemo> ticketMemoOptional) {

        if (ticketMemoOptional.isEmpty()) {
            throw new CustomException(CommonCode.NONEXISTENT_TICKET_MEMO);
        }
    }

    /**
     * 이미지 or 내용이 모두 존재하지 않는 경우 처리하는 메서드
     */
    private void checkExistImageOrContent(MultipartFile multipartFile, String content) {

        if (multipartFile == null && content == null) {
            throw new CustomException(CommonCode.EMPTY_CONTENT);
        }
    }

    /**
     * 지니캐시 지급하는 메서드
     */
    private void giveGenieCash(String email, String content) {

        int contentLength = 100; // 100자 이상
        int cash = 100; // 100 캐시 지급

        // 글자수가 100자를 넘기는 경우에만 지니 캐시 지급
        if (content.length() >= contentLength) {
            userRepository.updateUserCash(email, cash);
        }
    }

    /**
     * 보관함 상태인지 확인하는 메서드
     */
    private void checkCarrierType(MyCarrier myCarrier) {

        if (myCarrier.getType().equals(CarrierType.CARRIER)) {
            throw new CustomException(CommonCode.NOT_STORAGE_TYPE);
        }
    }
}
