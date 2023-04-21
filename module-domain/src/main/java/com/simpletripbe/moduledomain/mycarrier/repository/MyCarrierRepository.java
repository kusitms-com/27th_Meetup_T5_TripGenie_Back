package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyCarrierRepository extends JpaRepository<MyCarrier, Long>, MyCarrierRepositoryCustom {

    @Modifying
    @Query(value = "update MyCarrier m " +
            "set m.country = :#{#carrier.country}," +
            "m.image = :#{#carrier.image}," +
            "m.file = :#{#carrier.file}," +
            "m.link = :#{#carrier.link}" +
            "where m.id = :#{#carrier.id}", nativeQuery = true)
    void updateCarrier(@Param("carrier") CarrierListDTO carrierListDTO);

    @Modifying
    @Query(value = "update MyCarrier m " +
            "set m.dbsts = 'S'" +
            "where m.id = :id", nativeQuery = true)
    void deleteCarrier(@Param("id") Long id);

}
