package com.sogeti.filmland.security;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    private static String userEmail;

    public JwtRequestFilter(JwtService jwtService, UserDetailsService udService) {
        this.jwtService = jwtService;
        this.userDetailsService = udService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest
                                            request,
                                    @NonNull HttpServletResponse
                                            response,
                                    @NonNull FilterChain
                                            filterChain) throws ServletException, IOException {
        final String authorizationHeader =
                request.getHeader("Authorization");
        String jwt = null;
        String usernameJwt = null;

        if (authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            usernameJwt = jwtService.extractUsername(jwt);

        }
        if (usernameJwt != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails =
                    this.userDetailsService.loadUserByUsername(usernameJwt);
            if (Boolean.TRUE.equals(jwtService.validateToken(jwt, userDetails))) {
                UsernamePasswordAuthenticationToken
                        usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
