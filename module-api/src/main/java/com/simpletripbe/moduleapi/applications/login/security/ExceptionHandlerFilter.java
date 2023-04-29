package com.simpletripbe.moduleapi.applications.login.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpletripbe.modulecommon.common.response.CommonCode;
import com.simpletripbe.modulecommon.common.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e.getMessage(), e.getCommonCode());
        } catch (EmptyJwtTokenException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e.getMessage(), e.getCommonCode());
        }
    }

    private void setErrorResponse(
            HttpStatus status,
            HttpServletResponse response,
            String exceptionMessage,
            CommonCode commonCode
    ) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                commonCode,
                exceptionMessage
        );
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

