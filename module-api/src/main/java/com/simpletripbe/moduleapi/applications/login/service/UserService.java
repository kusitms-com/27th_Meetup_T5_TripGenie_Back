package com.simpletripbe.moduleapi.applications.login.service;

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

}
