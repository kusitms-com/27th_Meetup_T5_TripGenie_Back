package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mycarrier.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class MyCarrierRepositoryCustomImpl extends QuerydslRepositorySupport implements MyCarrierRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public MyCarrierRepositoryCustomImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(MyCarrier.class);
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<String> findAllByEmail(String email) {

        QMyCarrier q = QMyCarrier.myCarrier;
        QCarrierCountry c = QCarrierCountry.carrierCountry;

        List<Country> results = jpaQueryFactory
                .select(c.name).distinct()
                .from(c)
                .leftJoin(c.carrierId,q)
                .where(q.deleteYn.eq("N").and(q.email.eq(email)))
                .fetch();

        return results.stream().map(Country::getName).collect(Collectors.toList());

    }

    @Override
    public List<Ticket> findTicketByEmail(String email) {

        QMyCarrier q = QMyCarrier.myCarrier;
        QTicket t = QTicket.ticket;

        List<Ticket> results = jpaQueryFactory
                .selectFrom(t)
                .leftJoin(t.carrierId, q)
                .distinct()
                .where(
                        q.deleteYn.eq("N").and(q.email.eq(email))
                )
                .fetch();

        return results;

    }

}
