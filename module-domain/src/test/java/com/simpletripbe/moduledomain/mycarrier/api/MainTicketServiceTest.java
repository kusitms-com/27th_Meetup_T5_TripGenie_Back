package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoRes;
import com.simpletripbe.moduledomain.mycarrier.entity.*;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.TicketMemoRepository;
import com.simpletripbe.moduledomain.mycarrier.repository.TicketRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class MainTicketServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketMemoRepository ticketMemoRepository;

    @Autowired
    MyCarrierRepository myCarrierRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MainTicketService mainTicketService;

    @Autowired
    AwsS3Service awsS3Service;


    //given
    //when
    //then
    @Test
    void insertTicketMemo() throws FileUploadException {

        //given
        User user1 = createUser("a");
        User user2 = createUser("b");

        MyCarrier myCarrier1 = createMyCarrier(user1, 1L);
        MyCarrier myCarrier2 = createMyCarrier(user2, 2L);

        Ticket ticket1 = createTicket(myCarrier1, 1L);
        Ticket ticket2 = createTicket(myCarrier2, 2L);

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        myCarrierRepository.save(myCarrier1);
        myCarrierRepository.save(myCarrier2);

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);

        TicketMemoDTO ticketMemoDTO1 = TicketMemoDTO.builder()
                .carrierId(myCarrier1.getId())
                .ticketId(ticket1.getId())
                .content("test")
                .build();

        TicketMemoDTO ticketMemoDTO2 = TicketMemoDTO.builder()
                .carrierId(myCarrier2.getId())
                .ticketId(ticket2.getId())
                .content("abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde"
                        + "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde")
                .build();

        //when
        mainTicketService.insertTicketMemo(user1.getEmail(), ticketMemoDTO1, null);
        mainTicketService.insertTicketMemo(user2.getEmail(), ticketMemoDTO2, null);

        User resultUser1 = userRepository.findById(user1.getEmail()).get();
        User resultUser2 = userRepository.findById(user2.getEmail()).get();

        //then
        Assertions.assertThat(savedUser1.getCash()).isEqualTo(resultUser1.getCash());
        Assertions.assertThat(savedUser2.getCash() + 100).isEqualTo(resultUser2.getCash());

    }

    @Test
    void selectTicketMemo() {

        //given
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);
        Ticket ticket = createTicket(myCarrier, 1L);
        TicketMemo ticketMemo = createTicketMemo(ticket);

        userRepository.save(user);
        myCarrierRepository.save(myCarrier);
        ticketRepository.save(ticket);
        TicketMemo savedTicketMemo = ticketMemoRepository.save(ticketMemo);

        //when
        TicketMemoRes result = mainTicketService.selectTicketMemo(user.getEmail(), myCarrier.getId(), ticket.getId());

        //then
        Assertions.assertThat(savedTicketMemo.getContent()).isEqualTo(result.getContent());

    }

    @Test
    void updateTicketMemo() throws FileUploadException {

        //given
        String updateString = "갱신 데이터";
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);
        Ticket ticket = createTicket(myCarrier, 1L);
        TicketMemo ticketMemo = createTicketMemo(ticket);

        userRepository.save(user);
        myCarrierRepository.save(myCarrier);
        ticketRepository.save(ticket);
        ticketMemoRepository.save(ticketMemo);

        TicketMemoDTO ticketMemoDTO = TicketMemoDTO.builder()
                .carrierId(myCarrier.getId())
                .ticketId(ticket.getId())
                .content(updateString)
                .build();

        //when
        mainTicketService.updateTicketMemo(user.getEmail(), ticketMemoDTO, null);
        TicketMemo result = ticketMemoRepository.findByTicketId(ticket.getId()).get();

        //then
        Assertions.assertThat(result.getContent()
        ).isEqualTo(updateString);

    }

    @Test
    void deleteTicketMemo() {

        //given
        User user = createUser("a");
        MyCarrier myCarrier = createMyCarrier(user, 1L);
        Ticket ticket = createTicket(myCarrier, 1L);
        TicketMemo ticketMemo = createTicketMemo(ticket);
        ticketMemoRepository.save(ticketMemo);

        //when
        mainTicketService.deleteTicketMemo(user.getEmail(), myCarrier.getId(), ticket.getId());
        Optional<TicketMemo> result = ticketMemoRepository.findByTicketId(ticketMemo.getId());

        //then
        Assertions.assertThat(result.isEmpty());

    }

    User createUser(String email) {
        return User.builder()
                .email(email)
                .name("asdf")
                .nickname("qwer")
                .cash(0)
                .roles("ROLE_USER")
                .build();
    }

    MyCarrier createMyCarrier(User user, Long id) {
        return MyCarrier.builder()
                .id(id)
                .user(user)
                .name("일본여행캐리어")
                .startDate(LocalDate.of(2023, 5, 19))
                .endDate(LocalDate.of(2023, 5, 23))
                .deleteYn("n")
                .type(CarrierType.CARRIER)
                .build();
    }

    Ticket createTicket(MyCarrier myCarrier, Long id) {
        return Ticket.builder()
                .id(id)
                .myCarrier(myCarrier)
                .ticketUrl("asdf.com")
                .type(TicketType.IMAGE)
                .title("title")
                .sequence(1)
                .build();
    }

    TicketMemo createTicketMemo(Ticket ticket) {
        return TicketMemo.builder()
                .id(1L)
                .ticket(ticket)
                .content("asdf")
                .build();
    }
}