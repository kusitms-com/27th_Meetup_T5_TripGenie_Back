package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.QMyCarrier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<MyCarrier> findAllByDbsts() {

        QMyCarrier q = QMyCarrier.myCarrier;

        List<MyCarrier> results = jpaQueryFactory
                .selectFrom(q)
                .where(q.dbsts.eq("A"))
                .fetch();

        return results;

    }
}
