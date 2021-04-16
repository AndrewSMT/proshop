package com.andrew.proshop.security;

import com.andrew.proshop.constatns.ErrorCodes;
import com.andrew.proshop.exception.AuthenticationException;
import com.andrew.proshop.model.Role;
import com.andrew.proshop.model.User;
import com.andrew.proshop.model.UserStatus;
import com.andrew.proshop.security.token.JwtTokenService;
import com.andrew.proshop.security.token.TokenRequest;
import com.andrew.proshop.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Component
public class SecurityFacade {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtTokenService tokenService;
    private final SecurityService securityService;
    private final UserService userService;

    public String createAuthenticationTokenWithLogging(String username,
                                                       String password,
                                                       boolean clientAuthentication) {

        final List<String> userAuthorities = new ArrayList<>();

        List<String> authorities = securityService.authenticate(username, password);

        userAuthorities.addAll(getRoles(authorities, clientAuthentication));

        User user = userService.findByLogin(username);

        if (user.getStatus() == UserStatus.BANNED) {
            throw new AuthenticationException("User is banned", ErrorCodes.USER_BANNED);
        }

        TokenRequest tokenRequest = TokenRequest.builder()
                .login(username)
                .userId(user.getId())
                .authorities(userAuthorities)
                .build();

        return tokenService.generateToken(tokenRequest);
    }

    private List<String> getRoles(List<String> authorities, boolean clientAuthentication) {
        return authorities.stream()
                .filter(authority -> clientAuthentication == authority.equals(Role.RoleEnum.ROLE_CLIENT.toString()))
                .collect(toList());
    }

    public void authenticate(String token) {
        tokenService.getOwner(token).ifPresent(owner -> securityService.setAuthentication(owner.getUsername(), owner.getAuthorities()));
    }

    public boolean authenticationIsRequired() {
        return securityService.authenticationIsRequired();
    }

    public boolean tokenIsValid(String token) {
        return tokenService.tokenIsValid(token);
    }

    public Optional<String> getAuthorizationToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER))
                .map(authHeader -> authHeader.substring(7));
    }

    public void logoutUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }
    }
}
