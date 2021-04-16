package com.andrew.proshop.security;

import com.andrew.proshop.constatns.ErrorCodes;
import com.andrew.proshop.exception.AuthenticationException;
import com.andrew.proshop.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;

    public SecurityServiceImpl(
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public List<String> authenticate(String login, String password) {

        Objects.requireNonNull(login);
        Objects.requireNonNull(password);

        try {

            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);

            final Authentication authentication = authenticationManager.authenticate(token);

            return authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

        } catch (DisabledException e) {
            throw new AuthenticationException("User is not active!", e, ErrorCodes.USER_INACTIVE);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e, ErrorCodes.BAD_CREDENTIALS);
        }
    }

    public void setAuthentication(String username, List<String> authorities) {

        final List<SimpleGrantedAuthority> grantedAuthorities = authorities
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        final UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Override
    public boolean authenticationIsRequired() {
        return Objects.isNull(SecurityContextHolder.getContext().getAuthentication());
    }

}
