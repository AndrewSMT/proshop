package com.andrew.proshop.security;

import java.util.List;

public interface SecurityService {

    List<String> authenticate(String login, String password);

    void setAuthentication(String username, List<String> authorities);

    boolean authenticationIsRequired();
}
