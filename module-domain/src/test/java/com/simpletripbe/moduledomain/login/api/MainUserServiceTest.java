package com.simpletripbe.moduledomain.login.api;

import com.simpletripbe.modulecommon.common.response.CommonResponse;
import com.simpletripbe.moduledomain.login.dto.SignUpReq;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.jwt.JwtTokenProvider;
import com.simpletripbe.moduledomain.login.redis.RedisService;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Optional;

@SpringBootTest
class MainUserServiceTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    MainUserService mainUserService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisService redisService;

    @Test
    void signUp() {

        //given
        User user = new User("a@asdf.com"); // 임시 회원가입 된 상태에서 진행
        userRepository.save(user);

        SignUpReq signUpReq = SignUpReq.builder()
                .email(user.getEmail())
                .name("testName")
                .nickname("testNickname")
                .pictureUrl("asdf.com")
                .build();

        //when
        mainUserService.signUp(signUpReq);

        //then
        Optional<User> userOptional = userRepository.findById(user.getEmail());
        Assertions.assertThat(userOptional.get().getNickname()).isEqualTo("testNickname");
    }

    @Test
    void logout() {

        //given
        User user = createUser("a@asdf.com");
        userRepository.save(user);

        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        redisService.setValues(user.getEmail(), refreshToken, Duration.ofMinutes(10));

        //when
        mainUserService.logout(user.getEmail());

        //then
        String result = redisService.getValues(user.getEmail());
        org.junit.jupiter.api.Assertions.assertNull(result);
    }

    @Test
    void reissue() {
        
        //given
        User user = createUser("a@asdf.com");
        userRepository.save(user);

        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        redisService.setValues(user.getEmail(), refreshToken, Duration.ofMinutes(10));
        
        //when
        CommonResponse commonResponse = mainUserService.reissue(refreshToken);

        //then
        Object result = commonResponse.getAttribute().get("accessToken");
        org.junit.jupiter.api.Assertions.assertNotNull(result);

    }

    private User createUser(String email) {
        return User.builder()
                .email(email)
                .name("asdf")
                .nickname("qwer")
                .cash(0)
                .roles("ROLE_USER")
                .deleteYn("N")
                .build();
    }

}