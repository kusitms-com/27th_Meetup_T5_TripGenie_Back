package com.simpletripbe.moduleapi.applications.login.jwt;

import com.simpletripbe.moduleapi.applications.login.security.InvalidTokenException;
import com.simpletripbe.moduleapi.applications.login.service.CustomUserDetailsService;
import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;
    private Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L*60*30; // 30분
    private Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L*60*60*24*100; // 100일
    private Key key;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User user) {
        return generateToken(user, ACCESS_TOKEN_EXPIRE_LENGTH);
    }
    public String generateRefreshToken(User user) {
        return generateToken(user, REFRESH_TOKEN_EXPIRE_LENGTH);
    }

    // 토큰 생성
    public String generateToken(User user, long tokenExpireLength) {

        // payload에 들어갈 정보
        Claims claims = Jwts.claims().setSubject(user.getEmail());

        // 토큰 만료시간 설정
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenExpireLength);

        // 토큰 생성하고 리턴
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new InvalidTokenException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new InvalidTokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new InvalidTokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new InvalidTokenException("JWT 토큰이 잘못되었습니다.");
        }
    }

    // Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    // 토큰 파싱
    private String getUserEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

}
