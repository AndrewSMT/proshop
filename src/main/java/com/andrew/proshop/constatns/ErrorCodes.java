package com.andrew.proshop.constatns;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCodes {

    public static final String BAD_CREDENTIALS = "BAD_CREDENTIALS";
    public static final String USER_INACTIVE = "USER_INACTIVE";
    public static final String USER_BANNED = "USER_BANNED";
}
