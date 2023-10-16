package com.sogeti.filmland.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationInfo {

    public static String getCurrentUserEmail() {
        if(SecurityContextHolder.getContext() != null &&
            SecurityContextHolder.getContext().getAuthentication() != null) {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        throw new IllegalStateException("Not logged in");
    }
}
