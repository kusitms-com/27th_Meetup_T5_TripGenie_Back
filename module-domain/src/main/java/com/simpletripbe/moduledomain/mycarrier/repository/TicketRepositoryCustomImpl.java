package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mycarrier.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketRepositoryCustomImpl implements TicketRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Ticket> findAllByCarrierIdOrderByCreatedDateDesc(Long carrierId) {

        QTicket qt = QTicket.ticket;

        List<Ticket> results = jpaQueryFactory
                .select(qt)
                .from(qt)
                .where(qt.myCarrier.id.eq(carrierId))
                .orderBy(qt.createdDate.asc())
                .fetch();

        return results;

    }

}