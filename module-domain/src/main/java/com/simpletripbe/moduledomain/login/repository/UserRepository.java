package com.simpletripbe.moduledomain.login.repository;

import com.simpletripbe.moduledomain.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
