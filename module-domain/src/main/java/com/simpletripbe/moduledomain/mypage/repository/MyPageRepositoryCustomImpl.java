package com.simpletripbe.moduledomain.mypage.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import com.simpletripbe.moduledomain.mypage.entity.QMyPage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MyPageRepositoryCustomImpl extends QuerydslRepositorySupport implements MyPageRepositoryCustom{

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public MyPageRepositoryCustomImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(MyPage.class);
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public MyPage findProfileByNickname(String nickname) {

        QMyPage m = QMyPage.myPage;

        return jpaQueryFactory
                .selectFrom(m)
                .where(m.nickname = nickname)
                .fetch();
    }

    @Override
    public List<MyPage> findDocumentByNickname(String nickname) {
        QMyPage m = QMyPage.myPage;

        return jpaQueryFactory
                .selectFrom(m)
                .where(m.nickname = nickname)
                .fetch();
    }
}
