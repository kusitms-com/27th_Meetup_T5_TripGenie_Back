package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.QMyCarrier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MyCarrierRepositoryCustomImpl implements MyCarrierRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MyCarrierRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<String> findAllByDbsts() {

        QMyCarrier q = QMyCarrier.myCarrier;

        List<String> results = jpaQueryFactory
                .select(q.country).distinct()
                .from(q)
                .where(q.dbsts.eq("A"))
                .fetch();

        return results;

    }

    @Override
    public List<MyCarrier> findAllByCountry(String country) {

        QMyCarrier q = QMyCarrier.myCarrier;

        List<MyCarrier> results = jpaQueryFactory
                .selectFrom(q)
                .distinct()
                .where(
                        q.dbsts.eq("A").and(q.country.eq(country))
                )
                .fetch();

        return results;

    }

}
