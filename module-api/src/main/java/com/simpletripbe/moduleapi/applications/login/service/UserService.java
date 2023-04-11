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
        // 나중에는 보안처리를 위해 pw는 hash로 저장할 예정. 그렇게 된다면 비밀번호 일치 여부도 hash 값으로 해야함
        if (!user.getPassword().equals(password)) throw new CustomException(CommonCode.WRONG_PASSWORD);
        return user;
    }

}
