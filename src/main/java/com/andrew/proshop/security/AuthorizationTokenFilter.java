package com.andrew.proshop.security;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class AuthorizationTokenFilter extends GenericFilterBean {

    private final SecurityFacade securityFacade;

    public AuthorizationTokenFilter(SecurityFacade securityFacade) {
        this.securityFacade = securityFacade;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Optional<String> tokenOpt = securityFacade.getAuthorizationToken((HttpServletRequest) servletRequest);
        if (tokenOpt.isPresent()) {
            final String token = tokenOpt.get();
            if (securityFacade.authenticationIsRequired() && securityFacade.tokenIsValid(token)) {
                securityFacade.authenticate(token);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
