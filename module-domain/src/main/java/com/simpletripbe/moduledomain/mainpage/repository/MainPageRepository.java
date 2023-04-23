package com.simpletripbe.moduledomain.mainpage.repository;

import com.simpletripbe.moduledomain.mainpage.entity.MainPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainPageRepository extends JpaRepository<MainPage, Long> {
}
