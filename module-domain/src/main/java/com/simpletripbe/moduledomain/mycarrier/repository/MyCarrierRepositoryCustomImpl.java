package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.QMyCarrier;
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
    public List<String> findAllByDbSts() {

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
