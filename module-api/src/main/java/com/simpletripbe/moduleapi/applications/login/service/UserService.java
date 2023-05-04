package com.simpletripbe.moduleapi.applications.login.service;

import com.simpletripbe.moduleapi.applications.login.dto.SignUpReq;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtTokenProvider;
import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.live.rtk}")
    private Long REFRESH_TOKEN_EXPIRE_LENGTH;
    private final UserRepository userRepository;
    private final String DEFAULT_PICTURE_URL = "https://toppng.com//public/uploads/preview/user-account-management-logo-user-icon-11562867145a56rus2zwu.png";
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public void tmpSignUp(User user) {
        userRepository.save(user);
    }

    public String signUp(SignUpReq signUpReq) {

        // 닉네임이 없는 경우 예외 처리
        if (signUpReq.getNickname() == null) {
            throw new CustomException(CommonCode.EMPTY_NICKNAME);
        }

        // 이미지가 없는 경우 기본 이미지로 대체
        if (signUpReq.getPictureUrl() == null) {
            signUpReq.setPictureUrl(DEFAULT_PICTURE_URL);
        }

        Optional<User> userOptional = userRepository.findByEmail(signUpReq.getEmail());

        if (userOptional.isPresent()) {
            if (userOptional.get().getNickname() == null) {
                User user = User.builder()
                        .id(userOptional.get().getId())
                        .email(userOptional.get().getEmail())
                        .name(signUpReq.getName())
                        .nickname(signUpReq.getNickname())
                        .picture(signUpReq.getPictureUrl())
                        .gender(signUpReq.getGender())
                        .birth(signUpReq.getBirth())
                        .roles(userOptional.get().getRoles())
                        .build();

                userRepository.save(user);

                String refreshToken = jwtTokenProvider.generateRefreshToken(user);
                // 리프레시 토큰 저장
                redisService.setValues(user.getEmail(), refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_LENGTH));
                return refreshToken;
            } else { // 이미 회원가입이 완료된 경우
                throw new CustomException(CommonCode.USER_ALREADY_EXIST);
            }
        } else {
            throw new CustomException(CommonCode.WRONG_SIGNUP);
        }
    }

    public Map<String, String> signIn(User user) {

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        // 리프레시 토큰 저장
        redisService.setValues(user.getEmail(), refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_LENGTH));

        Map<String, String> token = new HashMap<>();
        token.put("accessToken", accessToken);
        token.put("refreshToken", refreshToken);

        return token;
    }

    public void logout(String email) {

        String rtkInRedis = redisService.getValues(email);

        if (!Objects.isNull(rtkInRedis)) {
            redisService.deleteValues(email);
        }
    }
}
