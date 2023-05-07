package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketRecordDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MyCarrierRepository extends JpaRepository<MyCarrier, Long>, MyCarrierRepositoryCustom {

    @Modifying
    @Query(value = "UPDATE MyCarrier m " +
            "SET m.country = :#{#carrier.country}," +
            "m.image = :#{#carrier.image}," +
            "m.file = :#{#carrier.file}," +
            "m.link = :#{#carrier.link}" +
            "WHERE m.id = :#{#carrier.id}", nativeQuery = true)
    void updateCarrier(@Param("carrier") CarrierListDTO carrierListDTO);

    @Modifying
    @Query(value = "UPDATE my_carrier m " +
            "SET m.dbsts = 'S' " +
            "WHERE m.mycarrier_id = :id", nativeQuery = true)
    void deleteCarrier(@Param("id") Long id);

    @Query(value = "select tm.content from TicketMemo tm " +
            "left outer join Ticket tc on tm.ticketId = tc.id " +
            "where tc.title = :ticket", nativeQuery = true)
    String selectMyTicketRecord(@Param("ticket") String ticket);

    @Query(value = "insert into MyPage m" + ":#{#record.content}" + "where m.country = :country", nativeQuery = true)
    void insertMyStampRecord(@Param("record") TicketRecordDTO record);

    @Modifying
    @Query(value = "update MyPage m set m.content = :#{#record.content} where m.country = :#{#record.country}", nativeQuery = true)
    void updateMyStampRecord(@Param("record") TicketRecordDTO record);

    @Transactional
    @Modifying
    @Query(value = "update MyPage m set m.dbsts = 'S' where m.country = :country", nativeQuery = true)
    void deleteMyStampRecord(@Param("country") String country);


}
