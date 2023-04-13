package com.simpletripbe.moduleapi.applications.login.service;

import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.login.dto.UserDTO;
import com.simpletripbe.moduledomain.login.dto.UserDetailDTO;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    public UserDTO saveUser(UserDetailDTO userDetailDto) {
        UserDTO newUser = new UserDTO();
        return userRepository.insertUser(newUser);
    }

}
