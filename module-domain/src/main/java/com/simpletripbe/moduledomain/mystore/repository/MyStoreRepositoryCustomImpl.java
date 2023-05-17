package com.simpletripbe.moduledomain.mystore.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.login.entity.QUser;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.mycarrier.entity.Country;
import com.simpletripbe.moduledomain.mycarrier.entity.QCarrierCountry;
import com.simpletripbe.moduledomain.mycarrier.entity.QMyCarrier;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class MyStoreRepositoryCustomImpl extends QuerydslRepositorySupport implements MyStoreRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public MyStoreRepositoryCustomImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(MyPage.class);
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Integer findPointByEmail(String email) {
        QUser u = QUser.user;

        List<Integer> results = jpaQueryFactory
                .select(u.cash)
                .from(u)
                .where(u.email.eq(email))
                .fetch();

        if (!results.isEmpty()) {
            return results.get(0);
        }

        return 0;
    }

}
