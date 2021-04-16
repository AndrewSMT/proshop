package com.andrew.proshop.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@AllArgsConstructor
@Configuration
public class AuthenticationManagerConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationManager = new DaoAuthenticationProvider();
        authenticationManager.setUserDetailsService(userDetailsService);
        authenticationManager.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(Collections.singletonList(authenticationManager));
    }

}
