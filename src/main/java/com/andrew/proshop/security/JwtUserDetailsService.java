package com.andrew.proshop.security;

import com.andrew.proshop.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userService.findByLogin(username))
                .map(UserPrincipal::create)
                .orElseThrow(() -> new UsernameNotFoundException(username + "does not exist"));
    }
}
