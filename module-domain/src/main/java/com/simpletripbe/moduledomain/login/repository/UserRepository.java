package com.simpletripbe.moduledomain.login.repository;

import com.simpletripbe.moduledomain.login.dto.UserDTO;
import com.simpletripbe.moduledomain.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Query(value = "select * from User user where user.email = :email", nativeQuery = true)
    UserDTO findByEmail(@Param("email") String email);

    @Query(value = "select * from User user where user.email = :email", nativeQuery = true)
    List<UserDTO> findAllByEmail(@Param("email") String email);

    @Query(value = "select * from User user where user.nickname = :nickname", nativeQuery = true)
    List<UserDTO> findAllByNickname(@Param("nickname") String nickname);

    @Query(value = "select * from User user where user.nickname = :nickname", nativeQuery = true)
    UserDTO insertUser(@Param("nickname") UserDTO userDTO);

}
