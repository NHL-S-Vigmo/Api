package com.nhlstenden.student.vigmo.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends GenericFilterBean {
    private final JWTProvider jwtProvider;

    public JWTFilter(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Get the token from the request
        String token = jwtProvider.getToken((HttpServletRequest) request);
        try {
            // If there was a token, and it is valid (e.g.: not expired)
            if (token != null && jwtProvider.validateToken(token)) {
                // Get the authentication instance and set it in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token));

                // Check if the token is about to expire, and we need to generate a fresh one
                String newToken = jwtProvider.getRefreshToken(token);
                if (newToken != null) {
                    // In that case we will return it on a different header
                    ((HttpServletResponse) response).addHeader("jwt-new-token", newToken);
                }
            }
        } catch (Exception e) {
            // Be sure to clear everything if something when wrong
            SecurityContextHolder.clearContext();
        }

        // Let the filter chain go on, authentication failure should not stop the filter.
        // It's up to Spring to decide whether the authentication in hte SecurityHolder is sufficient.
        chain.doFilter(request, response);
    }
}
