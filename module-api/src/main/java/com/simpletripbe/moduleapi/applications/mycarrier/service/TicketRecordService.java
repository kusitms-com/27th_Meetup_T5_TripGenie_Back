package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainTicketService;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketRecordService {

    private final MainTicketService mainTicketService;

    @Transactional
    public TicketMemoRes insertTicketMemo(String email, TicketMemoDTO ticketMemoDTO, MultipartFile multipartFile) throws FileUploadException {
        return mainTicketService.insertTicketMemo(email, ticketMemoDTO, multipartFile);
    }

    @Transactional
    public TicketMemoRes selectTicketMemo(String email, Long carrierId, Long ticketId) {
        return mainTicketService.selectTicketMemo(email, carrierId, ticketId);
    }

    @Transactional
    public TicketMemoRes updateTicketMemo(String email, TicketMemoDTO ticketMemoDTO, MultipartFile multipartFile) throws FileUploadException {
        return mainTicketService.updateTicketMemo(email, ticketMemoDTO, multipartFile);
    }

    @Transactional
    public void deleteTicketMemo(String email, Long carrierId, Long ticketId) {
        mainTicketService.deleteTicketMemo(email, carrierId, ticketId);
    }
}
