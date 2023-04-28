package com.simpletripbe.moduleapi.applications.login.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpletripbe.moduleapi.applications.login.jwt.JwtService;
import com.simpletripbe.moduleapi.applications.login.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String AUTHORITY_NAMES = "USER";

    private final ObjectMapper objectMapper;
    private final AdminMemberService adminMemberService;
    private final JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/v1/**")
            .authorizeRequests()
            .antMatchers("/api/v1/admin-members/login").permitAll()
            .anyRequest().hasAnyAuthority(AUTHORITY_NAMES);
        http.cors().configurationSource(corsConfigurationSource());
        http.csrf().disable()
            .logout().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .requestCache().disable()
            .addFilterAt(tokenPreAuthFilter(), AbstractPreAuthenticatedProcessingFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                    response.getOutputStream(),
                    "UNAUTHORIZED"
                );
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                    response.getOutputStream(),
                    "FORBIDDEN"
                );
            });
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
            "/h2-console/**",
            "/error",
            "/favicon.ico",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/hello"
        );
    }

    @Bean
    TokenPreAuthFilter tokenPreAuthFilter() {
        TokenPreAuthFilter tokenPreAuthFilter = new TokenPreAuthFilter();
        tokenPreAuthFilter.setAuthenticationManager(new ProviderManager(preAuthTokenProvider()));
        return tokenPreAuthFilter;
    }

    @Bean
    PreAuthTokenProvider preAuthTokenProvider() {
        return new PreAuthTokenProvider(
            adminMemberService,
            jwtService
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}