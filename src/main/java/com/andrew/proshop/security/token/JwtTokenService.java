package com.andrew.proshop.security.token;


import com.andrew.proshop.dto.user.UserDto;

import java.util.Optional;

public interface JwtTokenService {

    String generateToken(TokenRequest tokenRequest);

    boolean tokenIsValid(String token);

    Optional<UserDto> getOwner(String token);
}
