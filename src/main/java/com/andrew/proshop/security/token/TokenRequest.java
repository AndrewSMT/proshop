package com.andrew.proshop.security.token;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TokenRequest {

    private String login;
    private Long userId;
    private List<String> authorities;
}
