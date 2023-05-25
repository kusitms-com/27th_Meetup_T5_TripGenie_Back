package com.simpletripbe.moduledomain.mypage.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.login.entity.QUser;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import com.simpletripbe.moduledomain.mypage.entity.QMyPage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class MyPageRepositoryCustomImpl extends QuerydslRepositorySupport implements MyPageRepositoryCustom{

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public MyPageRepositoryCustomImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(MyPage.class);
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public User findProfileByNickname(String nickname) {

        QUser u = QUser.user;

        return jpaQueryFactory
                .selectFrom(u)
                .where(u.nickname.eq(nickname))
                .fetchOne();
    }

    @Override
    public String selectMyProfileImageByNickname(String nickname) {

        QUser u = QUser.user;

        String profileImage = jpaQueryFactory
                .select(u.picture)
                .from(u)
                .where(u.nickname.eq(nickname))
                .fetchOne();

        return profileImage != null ? profileImage : "EMPTY";
    }

    @Override
    public void updateMyNickname(MyPageProfileListDTO listDTO) {

        QUser u = QUser.user;

        jpaQueryFactory.update(u)
                .set(u.nickname, listDTO.getNickname())
                .where(u.email.eq(listDTO.getEmail()))
                .execute();
    }

    @Override
    public List<MyPage> findDocumentByNickname(String nickname) {

        QMyPage m = QMyPage.myPage;

        return jpaQueryFactory
                .selectFrom(m)
                .where(m.nickname.eq(nickname))
                .fetch();
    }

    @Override
    public List<MyPage> findStampByNickname(String nickname) {

        QMyPage m = QMyPage.myPage;

        return jpaQueryFactory
                .selectFrom(m)
                .where(m.nickname.eq(nickname))
                .fetch();
    }
}
