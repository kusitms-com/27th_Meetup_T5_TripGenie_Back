package com.simpletripbe.moduleapi.applications.login.service;

import com.simpletripbe.moduleapi.applications.login.dto.SignInReq;
import com.simpletripbe.moduleapi.applications.login.dto.SignUpReq;
import com.simpletripbe.moduleapi.applications.login.dto.TokenDTO;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtTokenProvider;
import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.login.dto.UserDTO;
import com.simpletripbe.moduledomain.login.dto.UserDetailDTO;
import com.simpletripbe.moduledomain.login.entity.User;
//import com.simpletripbe.moduledomain.login.mapper.UserMapper;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final String DEFAULT_PICTURE_URL = "https://toppng.com//public/uploads/preview/user-account-management-logo-user-icon-11562867145a56rus2zwu.png";
    private final JwtTokenProvider jwtTokenProvider;

//    private final UserMapper userMapper;

//    public UserDTO findUserByEmail(String email) {
//        UserDTO targetUser = userRepository.findByEmail(email);
//        return targetUser;
//    }
//
//    public UserDTO checkExistUser(String email, String password) {
//        UserDTO user = findUserByEmail(email);
//        if (user == null) throw new CustomException(CommonCode.NOT_EXIST_ID);
//
//        if (!user.getPassword().equals(password)) throw new CustomException(CommonCode.WRONG_PASSWORD);
//        return user;
//    }
//
//    public List<UserDTO> findAllUserByEmail(String email) {
//        return userRepository.findAllByEmail(email);
//    }
//
//    public List<UserDTO> findAllUserByNickname(String nickname) {
//        return userRepository.findAllByNickname(nickname);
//    }
//
//    public User saveUser(UserDetailDTO userDetailDto) {
//
//        User newUser = new User();
//        newUser = userMapper.toEntity(userDetailDto);
//
//        return userRepository.save(newUser);
//    }

    public User saveUser(SignUpReq signUpReq) {

        User user = User.builder()
                .email(signUpReq.getEmail())
                .name(signUpReq.getName())
                .nickname(signUpReq.getNickname())
                .picture(signUpReq.getPictureUrl())
                .gender(signUpReq.getGender())
                .birth(signUpReq.getBirth())
                .roles("ROLE_USER")
                .build();

        return userRepository.save(user);
    }

    public User signUp(SignUpReq signUpReq) {

        if (signUpReq.getPictureUrl() == null) {
            signUpReq.setPictureUrl(DEFAULT_PICTURE_URL);
        }

        return saveUser(signUpReq);

    }

    public String signIn(User user) {

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        // 리프레시 토큰 저장
        jwtTokenProvider.saveRefreshToken(user, refreshToken);

        // 인증 정보를 기반으로 jwt 토큰 생성하여 리턴
        return accessToken;
    }
}
