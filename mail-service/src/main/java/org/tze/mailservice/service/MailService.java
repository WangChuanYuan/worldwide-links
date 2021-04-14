package org.tze.mailservice.service;

import java.util.Map;

public interface MailService {

    void sendNoticeMail(String email, Map params);
    void sendMail(String to, String subject, String content);
}
