package com.andrew.proshop.security.token;

import com.andrew.proshop.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class ExpirationDateCalculator {

    private final JwtConfig JWT_CONFIG;

    public Instant calculateExpiredDate(Instant tokenCreatedDate) {
        ZoneId zoneId = ZoneId.of(JWT_CONFIG.getTokenTtlZoneId());

        ZonedDateTime nowTime = tokenCreatedDate.atZone(zoneId);
        LocalDate nowDate = LocalDate.ofInstant(tokenCreatedDate, zoneId);

        ZonedDateTime todayResetTime = nowDate.atStartOfDay().plusHours(JWT_CONFIG.getTimeOfDay()).atZone(zoneId);
        ZonedDateTime tomorrowResetTime = nowDate.atStartOfDay().plusDays(1).plusHours(JWT_CONFIG.getTimeOfDay()).atZone(zoneId);

        return nowTime.isBefore(todayResetTime)
                ? todayResetTime.toInstant()
                : tomorrowResetTime.toInstant();
    }
}
