package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.entity.TicketMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketMemoRepository extends JpaRepository<TicketMemo, Long> {
}
