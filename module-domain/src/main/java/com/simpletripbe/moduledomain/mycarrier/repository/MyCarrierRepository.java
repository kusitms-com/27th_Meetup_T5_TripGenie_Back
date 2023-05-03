package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

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
}
