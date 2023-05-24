package com.simpletripbe.moduledomain.login.repository;

import com.simpletripbe.moduledomain.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Query(value = "update user u set u.cash = u.cash + :cash, u.modified_date = CURRENT_TIMESTAMP where u.email = :email", nativeQuery = true)
    void updateUserCash(@Param("email") String email, @Param("cash") int cash);

    @Query(value = "select * from user u where u.email = :email and u.delete_yn = 'N'", nativeQuery = true)
    Optional<User> findByUserId(@Param("email") String email);

}
