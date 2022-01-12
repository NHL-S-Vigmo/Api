package com.nhlstenden.student.vigmo.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import java.io.IOException;

public class UserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write("{\"message\":\"You're not allowed in here.\"}");
    }
}
