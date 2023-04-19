package com.simpletripbe.moduleapi.applications.login.service;

import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.login.dto.UserDTO;
import com.simpletripbe.moduledomain.login.dto.UserDetailDTO;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.mapper.UserMapper;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO findUserByEmail(String email) {
        UserDTO targetUser = userRepository.findByEmail(email);
        return targetUser;
    }

    public UserDTO checkExistUser(String email, String password) {
        UserDTO user = findUserByEmail(email);
        if (user == null) throw new CustomException(CommonCode.NOT_EXIST_ID);

        if (!user.getPassword().equals(password)) throw new CustomException(CommonCode.WRONG_PASSWORD);
        return user;
    }

    public List<UserDTO> findAllUserByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    public List<UserDTO> findAllUserByNickname(String nickname) {
        return userRepository.findAllByNickname(nickname);
    }

    public User saveUser(UserDetailDTO userDetailDto) {

        User newUser = new User();
        newUser = userMapper.toEntity(userDetailDto);

        return userRepository.save(newUser);
    }

}
