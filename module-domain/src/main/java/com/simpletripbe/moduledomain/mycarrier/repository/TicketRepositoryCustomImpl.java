package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketEditDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketRepositoryCustomImpl implements TicketRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Ticket> findAllByCarrierIdOrderBySequenceAsc(Long carrierId) {

        QTicket qt = QTicket.ticket;

        List<Ticket> results = jpaQueryFactory
                .select(qt)
                .from(qt)
                .where(qt.myCarrier.id.eq(carrierId).and(qt.deleteYn.eq("N")))
                .orderBy(qt.sequence.asc())
                .fetch();

        return results;

    }

    @Override
    public void updateTicketSequence(List<TicketEditDTO> ticketEditDTOS) {

        QTicket qt = QTicket.ticket;

        ticketEditDTOS.forEach(t -> jpaQueryFactory.update(qt)
                .set(qt.sequence, t.getSequence())
                .where(qt.id.eq(t.getTicketId()))
                .execute());
    }
}