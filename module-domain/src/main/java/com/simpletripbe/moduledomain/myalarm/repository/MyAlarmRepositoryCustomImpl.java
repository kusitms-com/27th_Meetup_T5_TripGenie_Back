package com.simpletripbe.moduledomain.myalarm.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simpletripbe.moduledomain.batch.entity.QAlarm;
import com.simpletripbe.moduledomain.myalarm.dto.AlarmInfoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierSelectDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.QCarrierCountry;
import com.simpletripbe.moduledomain.mycarrier.entity.QMyCarrier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class MyAlarmRepositoryCustomImpl extends QuerydslRepositorySupport implements MyAlarmRepositoryCustom{

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public MyAlarmRepositoryCustomImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(MyCarrier.class);
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<AlarmInfoDTO> findAllByEmail(String email) {

        QAlarm a = QAlarm.alarm;

        List<Tuple> results = jpaQueryFactory
                .select(a.id, a.message, a.createdDate)
                .from(a)
                .where(a.user.email.eq(email))
                .fetch();

        return results.stream()
                .map(tuple -> new AlarmInfoDTO(
                        tuple.get(a.id),
                        tuple.get(a.message),
                        tuple.get(a.createdDate).toString()))
                .collect(Collectors.toList());

    }


}
