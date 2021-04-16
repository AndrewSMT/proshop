package com.andrew.proshop.dto.user;

import com.andrew.proshop.model.Role;
import com.andrew.proshop.model.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateUpdateUserDto {

    private String username;

    private String password;

    private String email;

    private UserStatus status;

    private List<Role.RoleEnum> roles;

}
