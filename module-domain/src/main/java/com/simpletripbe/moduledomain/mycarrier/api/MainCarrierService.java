package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.batch.dto.MyBagTicketDTO;
import com.simpletripbe.moduledomain.batch.dto.TicketListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.*;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierEdit.EditCarrierCountryResDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierEdit.EditCarrierNameResDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierEdit.EditCarrierPeriodResDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.*;
import com.simpletripbe.moduledomain.mycarrier.mapper.MyCarrierMapper;
import com.simpletripbe.moduledomain.mycarrier.repository.CarrierCountryRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.TicketMemoRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainCarrierService {

    private final MyCarrierRepository myCarrierRepository;
    private final CarrierCountryRepository carrierCountryRepository;
    private final TicketRepository ticketRepository;
    private final MyCarrierMapper myCarrierMapper;
    private final AwsS3Service awsS3Service;
    private final TicketMemoRepository ticketMemoRepository;

    public List<CarrierSelectDTO> selectAll(String email) {

        List<CarrierSelectDTO> entityResult = myCarrierRepository.findAllByEmail(email);

        return entityResult;
    }

    /**
     * 캐리어 정보 조회 서비스 로직
     */
    @Transactional
    public CarrierInfoRes getInfo(String email, Long carrierId) {

        MyCarrier myCarrier = checkValidCarrierId(email, carrierId);

        return myCarrierMapper.toCarrierInfoRes(myCarrier);
    }

    public List<TicketListDTO> selectCarrierList() {

        List<TicketListDTO> entityResult = myCarrierRepository.selectCarrierList();

        return entityResult;
    }

    public MyCarrier findMyCarrierById(Long carrierId) {
        return myCarrierRepository.findById(carrierId).orElseThrow(() -> new IllegalArgumentException("캐리어 정보를 찾을 수 없습니다."));
    }

    public List<MyBagTicketDTO> selectTicketList() {

        List<MyBagTicketDTO> entityResult = myCarrierRepository.selectTicketList();

        return entityResult;
    }
    public Ticket findTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("ticket이 존재하지 않습니다."));
    }

    public List<TicketTypeDTO> selectDetailAll(String email) {

        List<Ticket> entityResult = myCarrierRepository.findTicketByEmail(email);
        List<TicketTypeDTO> result = myCarrierMapper.toTicketTypeDto(entityResult);

        return result;
    }

    @Transactional
    public void addCarrier(CarrierListDTO carrierListDTO) {

        MyCarrier myCarrier = myCarrierMapper.toCarrierEntity(carrierListDTO);
        Country country = myCarrierMapper.toCountryEntity(carrierListDTO);

//        CarrierCountry carrierCountry = myCarrierMapper.toCarrierCountryEntity(carrierListDTO);

        CarrierCountry carrierCountry = new CarrierCountry();
        carrierCountry.setMyCarrier(myCarrier);
        carrierCountry.setCountry(country);

        myCarrierRepository.save(myCarrier);
        carrierCountryRepository.save(carrierCountry);
    }

    public void editCarrierName(EditCarrierNameResDTO carrierDTO) {

        myCarrierRepository.updateCarrierName(carrierDTO);
    }

    public void editCarrierPeriod(EditCarrierPeriodResDTO carrierDTO) {

        myCarrierRepository.updateCarrierPeriod(carrierDTO);
    }

    public void editCarrierCountry(EditCarrierCountryResDTO carrierDTO) {

        myCarrierRepository.updateCarrierCountry(carrierDTO);
    }

    public void deleteCarrier(DeleteResDTO deleteResDTO) {

        myCarrierRepository.deleteCarrier(deleteResDTO);
    }

    public void addStamp(CarrierListDTO carrierListDTO) {

        MyCarrier myCarrier = myCarrierMapper.toCarrierEntity(carrierListDTO);

        myCarrierRepository.save(myCarrier);
    }

    /**
     * 티켓 리스트 조회 서비스 로직
     */
    @Transactional
    public List<TicketDTO> selectTicketAll(String email, Long carrierId) {

        checkValidCarrierId(email, carrierId);

        List<Ticket> tickets = ticketRepository.findAllByCarrierIdOrderBySequenceAsc(carrierId);

        List<TicketDTO> result = myCarrierMapper.toTicketDTO(tickets);

        return result;
    }

    /**
     * 티켓 링크 저장 서비스 로직
     */
    @Transactional
    public List<TicketDTO> saveTicketUrl(String email, TicketUrlDTO ticketUrlDTO) {

        MyCarrier myCarrier = checkValidCarrierId(email, ticketUrlDTO.getId());

        checkCarrierType(myCarrier);

        ticketUrlDTO.setMapper(myCarrier, ticketUrlDTO.getUrl(), myCarrier.getTickets().size() + 1);

        Ticket ticket = myCarrierMapper.toTicketEntity(ticketUrlDTO);

        ticketRepository.save(ticket);

        return selectTicketAll(email, ticketUrlDTO.getId());
    }

    /**
     * 티켓 이미지, 파일 저장 서비스 로직
     */
    @Transactional
    public List<TicketDTO> saveTicketFile(String email, TicketUrlDTO ticketUrlDTO, MultipartFile multipartFile) throws FileUploadException {

        MyCarrier myCarrier = checkValidCarrierId(email, ticketUrlDTO.getId());

        checkCarrierType(myCarrier);

        String url = awsS3Service.uploadFile(multipartFile);

        ticketUrlDTO.setMapper(myCarrier, multipartFile.getOriginalFilename(), url, myCarrier.getTickets().size() + 1);

        Ticket ticket = myCarrierMapper.toTicketEntity(ticketUrlDTO);

        ticketRepository.save(ticket);

        return selectTicketAll(email, ticketUrlDTO.getId());

    }

    /**
     * 티켓 순서 변경 서비스 로직
     */
    @Transactional
    public void updateTicketOrder(String email, TicketEditListDTO ticketEditListDTO) {

        MyCarrier myCarrier = checkValidCarrierId(email, ticketEditListDTO.getCarrierId());

        checkValidTicketList(myCarrier, ticketEditListDTO.getTicketEditDTOList());

        ticketRepository.updateTicketSequence(ticketEditListDTO.getTicketEditDTOList());

    }

    /**
     * 티켓 이름 변경 서비스 로직
     */
    @Transactional
    public void updateTicketTitle(String email, TicketEditDTO ticketEditDTO) {

        MyCarrier myCarrier = checkValidCarrierId(email, ticketEditDTO.getCarrierId());

        checkValidTicketId(myCarrier, ticketEditDTO.getTicketId());

        ticketRepository.updateTicketTitle(ticketEditDTO);

    }

    /**
     * 티켓 삭제 서비스 로직
     */
    @Transactional
    public void deleteTicket(String email, Long carrierId, Long ticketId) {

        MyCarrier myCarrier = checkValidCarrierId(email, carrierId);

        checkValidTicketId(myCarrier, ticketId);

        checkExistTicketMemo(ticketId);

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketId);

        // 티켓 메모가 존재하면, 티켓 메모까지 soft delete
        if (ticketMemoOptional.isPresent()) {
            ticketMemoRepository.deleteTicketMemo(ticketMemoOptional.get().getId());
        }

        ticketRepository.deleteTicket(ticketId);

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
     * 전달받은 티켓 리스트들이 캐리어에 존재하는지 확인하는 메서드
     */
    private void checkValidTicketList(MyCarrier myCarrier, List<TicketEditDTO> ticketEditDTOList) {

        List<Ticket> tickets = myCarrier.getTickets();

        Set<Long> ticketIdSet = tickets.stream()
                .filter(t -> t.getDeleteYn().equals("N"))
                .map(Ticket::getId)
                .collect(Collectors.toSet());

        boolean existTicket = ticketEditDTOList.stream()
                .map(t -> t.getTicketId())
                .allMatch(ticketIdSet::contains);

        if (!existTicket) {
            throw new CustomException(CommonCode.NONEXISTENT_TICKET);
        }
    }

    /**
     * 전달받은 티켓이 캐리어에 존재하는지 확인하는 메서드
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
     * 티켓 메모가 존재하는 지 확인하는 메서드
     */
    private TicketMemo checkExistTicketMemo(Long ticketId) {

        Optional<TicketMemo> ticketMemoOptional = ticketMemoRepository.findByTicketId(ticketId);

        if (ticketMemoOptional.isPresent()) {
            return ticketMemoOptional.get();
        }

        return null;

    }

    /**
     * 캐리어 상태인지 확인하는 메서드
     */
    private void checkCarrierType(MyCarrier myCarrier) {

        if (myCarrier.getType().equals(CarrierType.STORAGE)) {
            throw new CustomException(CommonCode.NOT_CARRIER_TYPE);
        }
    }
}
