package com.simpletripbe.moduleapi.applications.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simpletripbe.moduledomain.login.api.MainUserService;
import com.simpletripbe.moduledomain.login.dto.SignUpReq;
import com.simpletripbe.modulecommon.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MainUserService mainUserService;

    @Transactional
    public CommonResponse signIn(String idToken) throws JsonProcessingException {
        return mainUserService.signIn(idToken);
    }

    @Transactional
    public CommonResponse signUp(SignUpReq signUpReq) {
        return mainUserService.signUp(signUpReq);
    }

    @Transactional
    public CommonResponse logout(String email) {
        return mainUserService.logout(email);
    }

    @Transactional
    public CommonResponse reissue(String refreshToken) {
        return mainUserService.reissue(refreshToken);
    }
}