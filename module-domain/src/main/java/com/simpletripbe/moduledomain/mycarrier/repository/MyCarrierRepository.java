package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface MyCarrierRepository extends JpaRepository<MyCarrier, Long>, MyCarrierRepositoryCustom {

    @Modifying
    @Query(value = "update MyCarrier m " +
            "set m.country = :country,m.image = :image, m.file = :file, m.link = :link" +
            "where m.id = :id", nativeQuery = true)
    void updateCarrier(@RequestBody CarrierListDTO carrierListDTO);

    @Modifying
    @Query(value = "update MyCarrier m " +
            "set m.dbsts = 'S'" +
            "where m.id = :id", nativeQuery = true)
    void deleteCarrier(@Param("id") Long id);

}
