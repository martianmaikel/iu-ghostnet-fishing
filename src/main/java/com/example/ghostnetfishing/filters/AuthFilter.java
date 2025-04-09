package com.example.ghostnetfishing.filters;

import com.example.ghostnetfishing.sessions.RecoveryPersonSession;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/restricted/*")
public class AuthFilter implements Filter {

    @Inject
    private RecoveryPersonSession session;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (session == null || session.getCurrentUser() == null) {
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }
}
