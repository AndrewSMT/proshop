package com.andrew.proshop.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtConfig {

    @Value("${security.jwt.token.ttl.zone-id}")
    private String tokenTtlZoneId;

    @Value("${security.jwt.token.ttl.time-of-day}")
    private long timeOfDay;

    @Value("${security.jwt.secret}")
    private String secret;
}
