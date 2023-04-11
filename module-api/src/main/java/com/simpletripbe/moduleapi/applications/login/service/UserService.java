package com.simpletripbe.moduleapi.applications.login.service;

import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.community.dto.UserDTO;
import com.simpletripbe.moduledomain.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
