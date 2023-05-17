package com.simpletripbe.moduleapi.applications.mycarrier.service;

import com.simpletripbe.moduledomain.mycarrier.api.MainCarrierService;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketOrderListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketUrlDTO;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyCarrierService {

    private final MainCarrierService mainCarrierService;

    @Transactional(readOnly = true)
    public List<String> selectAll(String email) {
        return mainCarrierService
                .selectAll(email);
    }

    @Transactional(readOnly = true)
    public void saveOne(CarrierListDTO carrierListDTO) {
        mainCarrierService.addCarrier(carrierListDTO);
    }

    @Transactional(readOnly = true)
    public void editOne(CarrierListDTO carrierListDTO) {
        mainCarrierService.editCarrier(carrierListDTO);
    }

    @Transactional(readOnly = true)
    public void deleteOne(String email) {
        mainCarrierService.deleteCarrier(email);
    }

    @Transactional(readOnly = true)
    public void saveStamp(CarrierListDTO carrierListDTO) {
        mainCarrierService.addStamp(carrierListDTO);
    }

    @Transactional
    public List<TicketDTO> saveUrl(String email, TicketUrlDTO ticketUrlDTO) {
        return mainCarrierService.saveTicketUrl(email, ticketUrlDTO);
    }

    @Transactional
    public List<TicketDTO> saveFile(String email, TicketUrlDTO ticketUrlDTO, MultipartFile multipartFile) throws FileUploadException {
        return mainCarrierService.saveTicketFile(email, ticketUrlDTO, multipartFile);
    }

    public List<TicketDTO> selectTicketAll(String email, Long carrierId) {
        return mainCarrierService
                .selectTicketAll(email, carrierId);
    }

    @Transactional
    public void updateTicketOrder(List<TicketOrderListDTO> ticketOrderListDTOS) {
        mainCarrierService.updateTicketOrder(ticketOrderListDTOS);
    }
}
