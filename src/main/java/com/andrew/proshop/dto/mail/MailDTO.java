package com.andrew.proshop.dto.mail;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDTO {

    private String emailTo;

    private String subject;

    private String message;
}
