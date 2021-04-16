package com.andrew.proshop.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestorePasswordDto {

    private String email;

    private String newPassword;

    private String repeatPassword;

    private String code;
}
