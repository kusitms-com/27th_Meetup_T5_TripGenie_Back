package com.simpletripbe.moduledomain.mainpage.repository;

import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import com.simpletripbe.moduledomain.mainpage.entity.MainPage;

import java.util.List;

public interface MainPageRepositoryCustom {

    List<MainPage> findBySort (OrderType orderType);

}
