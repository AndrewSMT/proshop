package com.andrew.proshop.dto.autentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthenticationRequestDto {
    private String login;
    private String password;
}
