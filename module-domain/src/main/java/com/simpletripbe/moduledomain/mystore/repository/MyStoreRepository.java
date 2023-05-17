package com.simpletripbe.moduledomain.mystore.repository;

import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyStoreRepository extends JpaRepository<MyPage, Long>, MyStoreRepositoryCustom {



}
