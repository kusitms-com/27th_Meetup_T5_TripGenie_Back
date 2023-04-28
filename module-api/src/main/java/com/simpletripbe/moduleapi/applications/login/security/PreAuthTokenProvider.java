package com.simpletripbe.moduleapi.applications.login.security;

import com.simpletripbe.moduleapi.applications.login.jwt.JwtService;
import com.simpletripbe.moduleapi.applications.login.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collections;

@RequiredArgsConstructor
public class PreAuthTokenProvider implements AuthenticationProvider {
    private final AdminMemberService adminMemberService;
    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            String token = authentication.getPrincipal().toString();
            Long adminMemberId = jwtService.decode(token);
            if (adminMemberId == null) {
                throw new TokenMissingException("Invalid token");
            }
            AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
            return new PreAuthenticatedAuthenticationToken(
                adminMemberId,
                "",
                Collections.singletonList(new SimpleGrantedAuthority(adminMember.getPosition().name()))
            );
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
