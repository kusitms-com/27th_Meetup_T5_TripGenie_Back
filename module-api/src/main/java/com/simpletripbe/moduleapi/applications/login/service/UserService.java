package com.simpletripbe.moduleapi.applications.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpletripbe.moduledomain.login.dto.TokenRes;
import com.simpletripbe.moduledomain.login.dto.UserInfoRes;
import com.simpletripbe.moduledomain.login.dto.SignUpReq;
import com.simpletripbe.moduledomain.login.dto.SocialOAuthDTO;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtTokenProvider;
import com.simpletripbe.modulecommon.common.exception.CustomException;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.modulecommon.common.response.CommonResponse;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.live.rtk}")
    private Long REFRESH_TOKEN_EXPIRE_LENGTH;
    @Value("${cloud.aws.s3.tripGenieProfileUrl}")
    private String DEFAULT_PICTURE_URL;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final OauthService oauthService;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 회원가입 서비스 로직
     */
    @Transactional
    public CommonResponse signUp(SignUpReq signUpReq) {

        // 닉네임이 없는 경우 예외 처리
        if (signUpReq.getNickname() == null) {
            throw new CustomException(CommonCode.EMPTY_NICKNAME);
        }

        // 이미지가 없는 경우 기본 이미지로 대체
        if (signUpReq.getPictureUrl() == null) {
            signUpReq.setPictureUrl(DEFAULT_PICTURE_URL);
        }

        Optional<User> userOptional = userRepository.findById(signUpReq.getEmail());

        if (userOptional.isPresent()) {
            if (userOptional.get().getNickname() == null) {
                User user = User.builder()
                        .email(userOptional.get().getEmail())
                        .name(signUpReq.getName())
                        .nickname(signUpReq.getNickname())
                        .picture(signUpReq.getPictureUrl())
                        .gender(signUpReq.getGender())
                        .birth(signUpReq.getBirth())
                        .roles(userOptional.get().getRoles())
                        .cash(0)
                        .build();

                userRepository.save(user);

                String accessToken = jwtTokenProvider.generateAccessToken(user);
                String refreshToken = jwtTokenProvider.generateRefreshToken(user);

                // 리프레시 토큰 저장
                redisService.setValues(user.getEmail(), refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_LENGTH));

                TokenRes tokenRes = new TokenRes(accessToken, refreshToken);

                Map result = objectMapper.convertValue(tokenRes, Map.class);

                return new CommonResponse(true, CommonCode.SIGNUP_SUCCESS, result);
            } else { // 이미 회원가입이 완료된 경우
                throw new CustomException(CommonCode.USER_ALREADY_EXIST);
            }
        } else {
            throw new CustomException(CommonCode.WRONG_SIGNUP);
        }
    }

    /**
     * 로그인 서비스 로직
     */
    @Transactional
    public CommonResponse signIn(String idToken) throws JsonProcessingException {

        SocialOAuthDTO socialOAuthDTO = oauthService.socialLogin(idToken);

        Optional<User> userOptional = userRepository.findById(socialOAuthDTO.getEmail());

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            // 필수 정보가 입력되지 않은 경우 -> 즉, 회원가입이 제대로 처리되지 않은 경우
            if (user.getNickname() == null) {

                String accessToken = jwtTokenProvider.generateAccessToken(user);
                UserInfoRes userInfoRes = new UserInfoRes(socialOAuthDTO, accessToken);
                Map result = objectMapper.convertValue(userInfoRes, Map.class);

                return new CommonResponse(true, CommonCode.SIGNUP_REQUIRED, result);
            } else { // 회원인 경우

                String accessToken = jwtTokenProvider.generateAccessToken(user);
                String refreshToken = jwtTokenProvider.generateRefreshToken(user);

                // 리프레시 토큰 저장
                redisService.setValues(user.getEmail(), refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_LENGTH));

                UserInfoRes userInfoRes = new UserInfoRes(socialOAuthDTO, accessToken, refreshToken);
                Map result = objectMapper.convertValue(userInfoRes, Map.class);

                return new CommonResponse(true, CommonCode.OAUTH_CHECK_SUCCESS, result);
            }
        } else {
            // 최초 로그인인 경우 -> 추가 정보를 입력받기 위해 jwt access token 생성을 위한 임시 회원가입
            User user = new User(socialOAuthDTO.getEmail());

            // 임시 회원가입
            userRepository.save(user);

            String accessToken = jwtTokenProvider.generateAccessToken(user);
            UserInfoRes userInfoRes = new UserInfoRes(socialOAuthDTO, accessToken);
            Map result = objectMapper.convertValue(userInfoRes, Map.class);

            return new CommonResponse(true, CommonCode.FIRST_TIME_LOGIN, result);
        }
    }

    /**
     * 로그아웃 서비스 로직
     */
    @Transactional
    public CommonResponse logout(String email) {

        String rtkInRedis = redisService.getValues(email);

        // redis에 존재하는 rtk 삭제
        if (!Objects.isNull(rtkInRedis)) {
            redisService.deleteValues(email);
        }

        return new CommonResponse(true, CommonCode.LOGOUT_SUCCESS);
    }

    /**
     * 토큰 재발급 서비스 로직
     */
    @Transactional
    public CommonResponse reissue(String refreshToken) {

        String accessToken = jwtTokenProvider.reissueAtk(refreshToken);
        TokenRes tokenRes = new TokenRes(accessToken);
        Map result = objectMapper.convertValue(tokenRes, Map.class);

        return new CommonResponse(true, CommonCode.REISSUE_SUCCESS, result);
    }
}