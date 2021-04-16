package com.andrew.proshop.service;

import com.andrew.proshop.dto.mail.MailDTO;

import javax.mail.MessagingException;

public interface NotificationService {

    void sendMail(MailDTO mailDTO) throws MessagingException;
}
