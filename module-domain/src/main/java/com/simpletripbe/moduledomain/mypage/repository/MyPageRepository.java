package com.simpletripbe.moduledomain.mypage.repository;

import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<MyPage, Long> {
}
