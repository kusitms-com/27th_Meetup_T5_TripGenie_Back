package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.TicketEditDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long>, TicketRepositoryCustom {

    @Modifying
    @Query(value = "update Ticket t set t.title = :#{#ticket.title} where t.ticket_id = :#{#ticket.ticketId}", nativeQuery = true)
    void updateTicketTitle(@Param("ticket") TicketEditDTO ticketEditDTO);

    @Modifying
    @Query(value = "update Ticket t SET t.delete_yn = 'Y' where t.ticket_id = :ticketId", nativeQuery = true)
    void deleteTicket(@Param("ticketId") Long ticketId);

}