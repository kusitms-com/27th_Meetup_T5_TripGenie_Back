package com.simpletripbe.moduledomain.mainpage.repository;

import com.simpletripbe.moduledomain.login.dto.UserDTO;
import com.simpletripbe.moduledomain.mainpage.dto.OrderType;
import com.simpletripbe.moduledomain.mainpage.entity.MainPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainPageRepository extends JpaRepository<MainPage, Long>, MainPageRepositoryCustom {

    @Query(value = "select * from mainpage mainpage where mainpage.country like %:searchWord%", nativeQuery = true)
    List<MainPage> findBySearchWord(@Param("searchWord") String searchWord);

}
