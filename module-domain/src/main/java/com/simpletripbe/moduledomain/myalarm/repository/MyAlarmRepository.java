package com.simpletripbe.moduledomain.myalarm.repository;

import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyAlarmRepository extends JpaRepository<MyCarrier, Long>, MyAlarmRepositoryCustom {
}
