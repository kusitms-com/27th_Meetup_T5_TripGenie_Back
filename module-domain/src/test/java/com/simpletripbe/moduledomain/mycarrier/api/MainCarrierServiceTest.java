package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import com.simpletripbe.moduledomain.mycarrier.dto.*;
import com.simpletripbe.moduledomain.mycarrier.entity.*;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.TicketRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainCarrierServiceTest {

    @Autowired
    MainCarrierService mainCarrierService;

    @Autowired
    MyCarrierRepository myCarrierRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

//    @Test
//    @DisplayName("전체 목록 조회 TEST")
//    void selectAll() {
//
//        //given
//        MyCarrier content = MyCarrier.builder()
//                .image("sampleImage")
//                .dbsts("A")
//                .link("sampleLink")
//                .country("Korea")
//                .file("sampleFile")
//                .createDate(LocalDateTime.now())
//                .build();
//        myCarrierRepository.save(content);
//
//        //when
//        List<String> result = mainCarrierService.selectAll();
//
//        //then
//        String resultInstance = result.get(0);
//        assertThat(resultInstance).isInstanceOf(String.class);
//        assertThat(result.size()).isEqualTo(1);
//
//    }

    @Test
    void selectCarrierList() {
    }

    @Test
    void selectTicketList() {
    }

    @Test
    void selectDetailAll() {
    }

    @Test
    void addCarrier() {
    }

    @Test
    void editCarrier() {
    }

    @Test
    void deleteCarrier() {
    }

    @Test
    void addStamp() {
    }

    @Test
    void selectTicketAll() {

        //given
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);

        userRepository.save(user);
        myCarrierRepository.save(myCarrier);

        // 티켓 5개 생성
        for (Long i = 1L; i <= 5; i++) {
            Ticket ticket = createTicket(myCarrier, i, i.intValue());
            ticketRepository.save(ticket);
        }

        //when
        List<TicketDTO> results = mainCarrierService.selectTicketAll(user.getEmail(), myCarrier.getId());

        //then
        Assertions.assertThat(results.size()).isEqualTo(5);

    }

    @Test
    void saveTicketUrl() {

        //given
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);

        userRepository.save(user);
        myCarrierRepository.save(myCarrier);

        TicketUrlDTO ticketUrlDTO = TicketUrlDTO.builder()
                .id(1L)
                .type(TicketType.LINK)
                .url("test.com")
                .build();

        //when
        List<TicketDTO> results = mainCarrierService.saveTicketUrl(user.getEmail(), ticketUrlDTO);

        //then
        Assertions.assertThat(results.size()).isEqualTo(1);

        Optional<Ticket> savedTicket = ticketRepository.findById(1L);
        org.junit.jupiter.api.Assertions.assertTrue(savedTicket.isPresent());

    }

    @Test
    void updateTicketOrder() {

        //given
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);

        Ticket ticket1 = createTicket(myCarrier, 1L, 1);
        Ticket ticket2 = createTicket(myCarrier, 2L, 2);

        userRepository.save(user);
        myCarrierRepository.save(myCarrier);
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);

        TicketEditDTO ticketEditDTO1 = TicketEditDTO.builder()
                .ticketId(ticket1.getId())
                .sequence(ticket2.getSequence())
                .build();

        TicketEditDTO ticketEditDTO2 = TicketEditDTO.builder()
                .ticketId(ticket2.getId())
                .sequence(ticket1.getSequence())
                .build();

        List<TicketEditDTO> ticketEditDTOList = new ArrayList<>();
        ticketEditDTOList.add(ticketEditDTO1);
        ticketEditDTOList.add(ticketEditDTO2);

        TicketEditListDTO ticketEditListDTO = TicketEditListDTO.builder()
                .carrierId(myCarrier.getId())
                .ticketEditDTOList(ticketEditDTOList)
                .build();

        //when
        mainCarrierService.updateTicketOrder(user.getEmail(), ticketEditListDTO);

        //then
        Optional<Ticket> ticket1Optional = ticketRepository.findById(ticket1.getId());
        Optional<Ticket> ticket2Optional = ticketRepository.findById(ticket2.getId());

        Assertions.assertThat(ticket1Optional.get().getSequence()).isEqualTo(2);
        Assertions.assertThat(ticket2Optional.get().getSequence()).isEqualTo(1);

    }

    @Test
    void updateTicketTitle() {

        //given
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);

        Ticket ticket = Ticket.builder()
                .id(1L)
                .myCarrier(myCarrier)
                .ticketUrl("asdf.com")
                .type(TicketType.IMAGE)
                .title("===beforeTitle===")
                .sequence(1)
                .deleteYn("N")
                .build();

        userRepository.save(user);
        myCarrierRepository.save(myCarrier);
        ticketRepository.save(ticket);

        TicketEditDTO ticketEditDTO = TicketEditDTO.builder()
                .carrierId(myCarrier.getId())
                .ticketId(ticket.getId())
                .title("===afterTitle===")
                .build();

        //when
        mainCarrierService.updateTicketTitle(user.getEmail(), ticketEditDTO);

        //then
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticket.getId());
        Assertions.assertThat(ticketOptional.get().getTitle()).isEqualTo("===afterTitle===");

    }

    @Test
    void deleteTicket() {

        //given
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);
        Ticket ticket = createTicket(myCarrier, 1L, 1);

        userRepository.save(user);
        myCarrierRepository.save(myCarrier);
        ticketRepository.save(ticket);

        //when
        mainCarrierService.deleteTicket(user.getEmail(), myCarrier.getId(), ticket.getId());

        //then
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticket.getId());
        Assertions.assertThat(ticketOptional.get().getDeleteYn()).isEqualTo("Y");

    }

    @Test
    void getInfo() {

        //given
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);

        userRepository.save(user);
        myCarrierRepository.save(myCarrier);

        //when
        CarrierInfoRes result = mainCarrierService.getInfo(user.getEmail(), myCarrier.getId());

        //then
        Assertions.assertThat(result.getName()).isEqualTo(myCarrier.getName());

    }

    private User createUser(String email) {
        return User.builder()
                .email(email)
                .name("asdf")
                .nickname("qwer")
                .cash(0)
                .roles("ROLE_USER")
                .deleteYn("N")
                .build();
    }

    private MyCarrier createMyCarrier(User user, Long id) {
        return MyCarrier.builder()
                .id(id)
                .user(user)
                .name("일본여행캐리어")
                .startDate(LocalDate.of(2023, 5, 19))
                .endDate(LocalDate.of(2023, 5, 23))
                .deleteYn("N")
                .type(CarrierType.CARRIER)
                .build();
    }

    private Ticket createTicket(MyCarrier myCarrier, Long id, Integer sequence) {
        return Ticket.builder()
                .id(id)
                .myCarrier(myCarrier)
                .ticketUrl("asdf.com")
                .type(TicketType.IMAGE)
                .title("title")
                .sequence(sequence)
                .deleteYn("N")
                .build();
    }
}