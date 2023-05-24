package com.simpletripbe.moduledomain.login.jwt;

import com.simpletripbe.modulecommon.common.exception.jwt.EmptyJwtTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        if (!httpServletRequest.getRequestURI().startsWith("/v1/user/oauth")) {
            String jwt = resolveToken(httpServletRequest);

            // 유효성 검증하여 올바른 토큰인 경우
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt); // 토큰에서 Authentication 객체를 받아와서
                SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 저장
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName());
            } else {
                log.debug("유효한 JWT 토큰이 없습니다, uri: {}");
                throw new EmptyJwtTokenException("유효한 JWT 토큰이 없습니다.");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
