package com.simpletripbe.moduledomain.community.repository;

import com.simpletripbe.moduledomain.community.dto.UserDTO;
import com.simpletripbe.moduledomain.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from User user where user.email = :email", nativeQuery = true)
    UserDTO findByEmail(@Param("email") String email);

}
