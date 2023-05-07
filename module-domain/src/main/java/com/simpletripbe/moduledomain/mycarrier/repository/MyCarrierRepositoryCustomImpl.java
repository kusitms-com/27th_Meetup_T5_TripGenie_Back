package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mycarrier.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
    public List<Country> findAllByDbSts() {

        QMyCarrier q = QMyCarrier.myCarrier;
        QCarrierCountry c = QCarrierCountry.carrierCountry;

        List<Country> results = jpaQueryFactory
                .select(c.name).distinct()
                .from(c)
                .leftJoin(q)
                .where(q.deleteYn.eq("N"))
                .fetch();

        return results;

    }

    @Override
    public List<CarrierCountry> findAllByCountry(String country) {

        QMyCarrier q = QMyCarrier.myCarrier;
        QCarrierCountry c = QCarrierCountry.carrierCountry;

        List<CarrierCountry> results = jpaQueryFactory
                .selectFrom(c)
                .leftJoin(q)
                .distinct()
                .where(
                        q.deleteYn.eq("N").and(q.name.eq(country))
                )
                .fetch();

        return results;

    }

}
