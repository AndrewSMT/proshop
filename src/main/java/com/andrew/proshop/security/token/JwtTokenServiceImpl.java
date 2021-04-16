package com.andrew.proshop.security.token;

import com.andrew.proshop.config.JwtConfig;
import com.andrew.proshop.dto.user.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final String CREATED_AT = "createdAt";
    private static final String EXPIRATION_DATE = "expirationDate";
    private static final String REFRESH_EXPIRATION_DATE = "refreshExpirationDate";
    private static final String AUTHORITIES_CLAIM = "AUTHORITIES_CLAIM";
    private static final String USER_ID = "userId";

    @Value("${security.jwt.secret}")
    private String secret;

    private final Clock clock;
    private final JwtConfig JWT_CONFIG;
    private final ExpirationDateCalculator expirationDateCalculator;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    @Override
    public Optional<UserDto> getOwner(String token) {

        final Claims claims = getAllClaimsFromToken(token);

        List<String> authorities = List.of();

        final String claim = claims.get(AUTHORITIES_CLAIM, String.class);

        if (claim != null && !claim.isEmpty()) {
            final String[] strings = claim.split(",");
            authorities = Arrays.asList(strings);
        }

        return Optional.of(
                UserDto.builder()
                        .username(claims.getSubject())
                        .authorities(authorities)
                        .build()
        );
    }

    @Override
    public String generateToken(TokenRequest tokenRequest) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_CLAIM, String.join(",", tokenRequest.getAuthorities()));
        claims.put(USER_ID, tokenRequest.getUserId());

        return doGenerateToken(claims, tokenRequest.getLogin());
    }

    @Override
    public boolean tokenIsValid(String token) {
        return !isTokenExpired(token);
    }


    private Long getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, (claims) -> (Long) claims.get(EXPIRATION_DATE));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_CONFIG.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        final Long expirationDate = getExpirationDateFromToken(token);
        return Instant.ofEpochMilli(expirationDate).isBefore(getInstant());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        setTimeline(claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, JWT_CONFIG.getSecret())
                .compact();
    }

    private void setTimeline(Map<String, Object> claims) {

        Instant createdDate = getInstant();
        Instant expirationDate = expirationDateCalculator.calculateExpiredDate(createdDate);

        claims.put(CREATED_AT, createdDate.toEpochMilli());
        claims.put(EXPIRATION_DATE, expirationDate.toEpochMilli());
        claims.put(REFRESH_EXPIRATION_DATE, createdDate.toEpochMilli());
    }

    private Instant getInstant() {
        return clock.instant();
    }

}
