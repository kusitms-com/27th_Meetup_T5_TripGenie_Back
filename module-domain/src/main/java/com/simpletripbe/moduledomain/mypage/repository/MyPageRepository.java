package com.simpletripbe.moduledomain.mypage.repository;

import com.simpletripbe.moduledomain.mypage.dto.StampRecordDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MyPageRepository extends JpaRepository<MyPage, Long>, MyPageRepositoryCustom {

    @Query(value = "select m.record from MyPage m where m.country = :country", nativeQuery = true)
    String selectMyStampRecord(@Param("country") String country);

    @Query(value = "insert into MyPage m" + ":#{#record.content}" + "where m.country = :country", nativeQuery = true)
    void insertMyStampRecord(@Param("record") StampRecordDTO record);

    @Modifying
    @Query(value = "update MyPage m set m.content = :#{#record.content} where m.country = :#{#record.country}", nativeQuery = true)
    void updateMyStampRecord(@Param("record") StampRecordDTO record);

    @Transactional
    @Modifying
    @Query(value = "update MyPage m set m.dbsts = 'S' where m.country = :country", nativeQuery = true)
    void deleteMyStampRecord(@Param("country") String country);

}
