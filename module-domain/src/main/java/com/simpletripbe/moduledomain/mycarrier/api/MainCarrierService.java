package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.batch.dto.MyBagTicketDTO;
import com.simpletripbe.moduledomain.batch.dto.TicketListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketTypeDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketUrlDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.CarrierCountry;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import com.simpletripbe.moduledomain.mycarrier.mapper.MyCarrierMapper;
import com.simpletripbe.moduledomain.mycarrier.repository.CarrierCountryRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainCarrierService {

    private final MyCarrierRepository myCarrierRepository;
    private final CarrierCountryRepository carrierCountryRepository;
    private final TicketRepository ticketRepository;
    private final MyCarrierMapper myCarrierMapper;
    private final AwsS3Service awsS3Service;

    public List<String> selectAll(String email) {

        List<String> entityResult = myCarrierRepository.findAllByEmail(email);

        return entityResult;
    }

    public List<TicketListDTO> selectCarrierList() {

        List<TicketListDTO> entityResult = myCarrierRepository.selectCarrierList();

        return entityResult;
    }

    public List<MyBagTicketDTO> selectTicketList() {

        List<MyBagTicketDTO> entityResult = myCarrierRepository.selectTicketList();

        return entityResult;
    }

    public List<TicketTypeDTO> selectDetailAll(String email) {

        List<Ticket> entityResult = myCarrierRepository.findTicketByEmail(email);
        List<TicketTypeDTO> result = myCarrierMapper.toTicketTypeDto(entityResult);

        return result;
    }

    public void addCarrier(CarrierListDTO carrierListDTO) {

        MyCarrier myCarrier = myCarrierMapper.toCarrierEntity(carrierListDTO);
        CarrierCountry carrierCountry = myCarrierMapper.toCarrierCountryEntity(carrierListDTO);

        myCarrierRepository.save(myCarrier);
        carrierCountryRepository.save(carrierCountry);
    }

    public void editCarrier(CarrierListDTO carrierListDTO) {

        myCarrierRepository.updateCarrier(carrierListDTO);
    }

    public void deleteCarrier(String email) {

        myCarrierRepository.deleteCarrier(email);
    }

    public void addStamp(CarrierListDTO carrierListDTO) {

        MyCarrier myCarrier = myCarrierMapper.toCarrierEntity(carrierListDTO);

        myCarrierRepository.save(myCarrier);
    }

    @Transactional
    public TicketUrlDTO saveTicketUrl(String email, TicketUrlDTO ticketUrlDTO) {

        Optional<MyCarrier> myCarrierOptional = myCarrierRepository.findById(ticketUrlDTO.getId());

        // 존재하지 않는 캐리어 예외처리
        if (myCarrierOptional.isEmpty()) {
            throw new CustomException(CommonCode.NONEXISTENT_CARRIER);
        }

        // 사용자의 캐리어가 아닌 경우 예외처리
        if (!myCarrierOptional.get().getUser().getEmail().equals(email)) {
            throw new CustomException(CommonCode.INVALID_CARRIER_ACCESS);
        }

        ticketUrlDTO.setMapper(myCarrierOptional.get(), ticketUrlDTO.getUrl());

        Ticket ticket = myCarrierMapper.toTicketEntity(ticketUrlDTO);

        ticketRepository.save(ticket);

        return new TicketUrlDTO(ticketUrlDTO.getUrl(), ticketUrlDTO.getTitle());
    }

    @Transactional
    public TicketUrlDTO saveTicketFile(String email, TicketUrlDTO ticketUrlDTO, MultipartFile multipartFile) throws FileUploadException {

        Optional<MyCarrier> myCarrierOptional = myCarrierRepository.findById(ticketUrlDTO.getId());

        // 존재하지 않는 캐리어 예외처리
        if (myCarrierOptional.isEmpty()) {
            throw new CustomException(CommonCode.NONEXISTENT_CARRIER);
        }

        // 사용자의 캐리어가 아닌 경우 예외처리
        if (!myCarrierOptional.get().getUser().getEmail().equals(email)) {
            throw new CustomException(CommonCode.INVALID_CARRIER_ACCESS);
        }

        String url = awsS3Service.uploadFile(multipartFile);

        ticketUrlDTO.setMapper(myCarrierOptional.get(), multipartFile.getOriginalFilename(), url);

        Ticket ticket = myCarrierMapper.toTicketEntity(ticketUrlDTO);

        ticketRepository.save(ticket);

        return new TicketUrlDTO(url, ticketUrlDTO.getTitle());

    }

    @Transactional
    public List<TicketDTO> selectTicketAll(String email, Long carrierId) {

        Optional<MyCarrier> myCarrierOptional = myCarrierRepository.findById(carrierId);

        // 존재하지 않는 캐리어 예외처리
        if (myCarrierOptional.isEmpty()) {
            throw new CustomException(CommonCode.NONEXISTENT_CARRIER);
        }

        // 사용자의 캐리어가 아닌 경우 예외처리
        if (!myCarrierOptional.get().getUser().getEmail().equals(email)) {
            throw new CustomException(CommonCode.INVALID_CARRIER_ACCESS);
        }

        List<Ticket> tickets = ticketRepository.findAllByCarrierIdOrderByCreatedDateDesc(carrierId);

        List<TicketDTO> result = myCarrierMapper.toTicketDTO(tickets);

        return result;
    }
}
