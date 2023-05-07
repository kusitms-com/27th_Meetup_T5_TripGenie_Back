package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.entity.CarrierCountry;
import com.simpletripbe.moduledomain.mycarrier.entity.Country;

import java.util.List;

public interface MyCarrierRepositoryCustom {

    List<Country> findAllByDbSts();


    List<CarrierCountry> findAllByCountry(String country);

}
