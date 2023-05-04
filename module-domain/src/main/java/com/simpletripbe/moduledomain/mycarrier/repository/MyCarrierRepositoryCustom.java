package com.simpletripbe.moduledomain.mycarrier.repository;

import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;

import java.util.List;

public interface MyCarrierRepositoryCustom {

    List<String> findAllByDbSts();


    List<MyCarrier> findAllByCountry(String country);

}
