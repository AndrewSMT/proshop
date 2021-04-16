package com.andrew.proshop.config;


import com.andrew.proshop.security.AuthorizationTokenFilter;
import com.andrew.proshop.security.SecurityFacade;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private SecurityFacade securityFacade;

    @Override
    public void configure(HttpSecurity httpSecurity){
        AuthorizationTokenFilter jwtTokenFilter = new AuthorizationTokenFilter(securityFacade);
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}