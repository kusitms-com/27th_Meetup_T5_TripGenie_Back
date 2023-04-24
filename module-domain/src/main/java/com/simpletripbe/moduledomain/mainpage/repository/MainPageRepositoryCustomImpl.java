package com.simpletripbe.moduledomain.mainpage.repository;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import com.simpletripbe.moduledomain.mainpage.entity.MainPage;
import com.simpletripbe.moduledomain.mainpage.entity.QMainPage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MainPageRepositoryCustomImpl extends QuerydslRepositorySupport implements MainPageRepositoryCustom{

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public MainPageRepositoryCustomImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(MainPage.class);
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<MainPage> findBySort(OrderType orderType) {

        QMainPage m = QMainPage.mainPage;

        return jpaQueryFactory
                .selectFrom(m)
                .orderBy(m.continent.desc())
                .fetch();

    }

}
