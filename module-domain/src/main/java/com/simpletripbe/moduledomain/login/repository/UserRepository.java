package com.simpletripbe.moduledomain.login.repository;

import com.simpletripbe.moduledomain.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Query(value = "update user u set u.cash = u.cash + :cash, u.modified_date = CURRENT_TIMESTAMP where u.email = :email", nativeQuery = true)
    void updateUserCash(@Param("email") String email, @Param("cash") int cash);
}
