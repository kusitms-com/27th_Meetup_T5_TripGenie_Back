package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.DeleteResDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.EditCarrierDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MyCarrierRepository extends JpaRepository<MyCarrier, Long>, MyCarrierRepositoryCustom {

    @Modifying
    @Query(value = "UPDATE my_carrier m " +
            "JOIN carrier_country cc ON m.carrier_id = cc.carrier_id " +
            "SET m.name = :#{#carrier.name}, " +
            "m.email = :#{#carrier.email}, " +
            "m.start_date = :#{#carrier.startDate}, " +
            "m.end_date = :#{#carrier.endDate} " +
            "WHERE m.email = :#{#carrier.email} AND cc.country_name = :#{#carrier.country}", nativeQuery = true)
    void updateCarrier(@Param("carrier") EditCarrierDTO carrierDTO);

    @Modifying
    @Query(value = "UPDATE my_carrier m " +
            "JOIN carrier_country cc ON m.carrier_id = cc.carrier_id " +
            "SET cc.country_name = :#{#carrier.country} " +
            "WHERE m.email = :#{#carrier.email} AND m.name = :#{#carrier.name} AND m.carrier_id = cc.carrier_id", nativeQuery = true)
    void updateCountry(@Param("carrier") EditCarrierDTO carrierDTO);

    @Modifying
    @Query(value = "UPDATE my_carrier m " +
            "JOIN ticket t ON m.carrier_id = t.carrier_id " +
            "SET m.delete_yn = 'Y', " +
            "t.delete_yn = 'Y' " +
            "WHERE m.email = :#{#carrier.email} AND m.name = :#{#carrier.name}", nativeQuery = true)
    void deleteCarrier(@Param("carrier") DeleteResDTO deleteResDTO);

    @Query(value = "select tm.content from TicketMemo tm " +
            "left outer join Ticket tc on tm.ticketId = tc.id " +
            "where tc.title = :ticket", nativeQuery = true)
    String selectMyTicketRecord(@Param("ticket") String ticket);

    @Query(value = "insert into MyPage m" + ":#{#record.content}" + "where m.country = :country", nativeQuery = true)
    void insertMyStampRecord(@Param("record") TicketMemoDTO record);

    @Modifying
    @Query(value = "update MyPage m set m.content = :#{#record.content} where m.country = :#{#record.country}", nativeQuery = true)
    void updateMyStampRecord(@Param("record") TicketMemoDTO record);

    @Transactional
    @Modifying
    @Query(value = "update MyPage m set m.dbsts = 'S' where m.country = :country", nativeQuery = true)
    void deleteMyStampRecord(@Param("country") String country);


}
