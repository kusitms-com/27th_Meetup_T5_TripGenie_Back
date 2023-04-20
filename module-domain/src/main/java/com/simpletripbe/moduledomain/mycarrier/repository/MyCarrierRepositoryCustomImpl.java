package com.simpletripbe.moduledomain.mycarrier.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.modulecommon.common.util.QueryUtils;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.QMyCarrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class MyCarrierRepositoryCustomImpl implements MyCarrierRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MyCarrierRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<CarrierListDTO> findAllbyPage(Pageable pageable) {

        final Sort sort = pageable.getSortOr(Sort.by(Sort.Direction.DESC, "createdAt"));

        QueryResults<CarrierListDTO> results = jpaQueryFactory
                .selectFrom(QMyCarrier)
                .orderBy(getOrderSpecifier(sort))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return QueryUtils.toPage(results, pageable);

    }
}
