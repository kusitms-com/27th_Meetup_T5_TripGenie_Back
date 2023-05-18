package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.EditCarrierDTO;
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
            "SET m.name = :#{#carrier.name}," +
            "m.email = :#{#carrier.email}," +
            "m.startDate = :#{#carrier.startDate}," +
            "m.endDate = :#{#carrier.endDate}" +
            "WHERE m.email = :#{#carrier.email}", nativeQuery = true)
    void updateCarrier(@Param("carrier") EditCarrierDTO carrierDTO);

    @Modifying
    @Query(value = "UPDATE MyCarrier m " +
            "SET m.deleteYn = 'Y' " +
            "WHERE m.email = :email", nativeQuery = true)
    void deleteCarrier(@Param("email") String email);

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
