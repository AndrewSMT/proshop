package com.andrew.proshop.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRoleDto {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("role_name")
    private String roleName;

}