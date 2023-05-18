package com.simpletripbe.moduledomain.mystore.repository;

import com.simpletripbe.moduledomain.mystore.dto.UpdatePointDTO;

import java.util.List;

public interface MyStoreRepositoryCustom {

    Integer findPointByEmail(String email);

    void updatePointByEmail(UpdatePointDTO pointDTO);

}
