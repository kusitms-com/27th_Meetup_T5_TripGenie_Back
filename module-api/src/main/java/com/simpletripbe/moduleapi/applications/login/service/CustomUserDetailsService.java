package com.simpletripbe.moduleapi.applications.login.service;

import com.simpletripbe.moduledomain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Value("${app.security.credential}")
    private String credential;
    private final UserRepository userRepository;

    /**
     * 사용자의 이메일을 기반으로 User 객체 생성 후 UserDetails 객체 반환하는 메서드
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findById(email)
                .map(user -> createUser(user))
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에 존재하지 않습니다."));
    }

    /**
     * User 객체 생성하는 메서드
     */
    private User createUser(com.simpletripbe.moduledomain.login.entity.User user) {

        List<GrantedAuthority> grantedAuthorities = user.getRoleList().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(user.getEmail(),
                credential,
                grantedAuthorities);
    }
}
