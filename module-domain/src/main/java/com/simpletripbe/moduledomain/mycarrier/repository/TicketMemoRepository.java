package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.TicketMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketMemoRepository extends JpaRepository<TicketMemo, Long> {

    Optional<TicketMemo> findByTicketId(Long ticketId);

    @Modifying
    @Query(value = "update TicketMemo tm set tm.content = :#{#ticketMemo.content}, tm.image_url = :#{#ticketMemo.imageUrl} where tm.id = :#{#ticketMemo.ticketMemoId}", nativeQuery = true)
    void updateTicketMemo(@Param("ticketMemo") TicketMemoDTO ticketMemo);
}
