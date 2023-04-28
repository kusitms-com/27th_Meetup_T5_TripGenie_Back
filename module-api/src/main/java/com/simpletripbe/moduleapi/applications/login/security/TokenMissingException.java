package com.simpletripbe.moduleapi.applications.login.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TokenMissingException extends UsernameNotFoundException {
    public TokenMissingException(String message) {
        super(message);
    }
}
