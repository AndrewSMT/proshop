package com.andrew.proshop.web.controller;

import com.andrew.proshop.dto.autentication.AuthenticationRequestDto;
import com.andrew.proshop.dto.autentication.AuthenticationResponseDto;
import com.andrew.proshop.dto.mail.RestoreMailDto;
import com.andrew.proshop.dto.user.CreateUpdateUserDto;
import com.andrew.proshop.dto.user.UserDto;
import com.andrew.proshop.security.SecurityFacade;
import com.andrew.proshop.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final SecurityFacade securityFacade;
    private final UserService userService;

    @PostMapping(value = "/login/")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
        final String token = securityFacade.createAuthenticationTokenWithLogging(requestDto.getLogin(), requestDto.getPassword(), false);
        return ResponseEntity.ok(AuthenticationResponseDto.builder().accessToken(token).build());
    }

    @PostMapping(value = "/register/")
    public ResponseEntity<?> register(@RequestBody CreateUpdateUserDto newUser) {
        UserDto user = userService.createUser(newUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout() {
        securityFacade.logoutUser();
        return ResponseEntity.ok().build();
    }

}
