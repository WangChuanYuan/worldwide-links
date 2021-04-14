package org.tze.mailservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tze.mailservice.service.MailService;

import java.util.Map;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/notice")
    public String sendNoticeMail(@RequestBody Map params) {
        String email = params.get("email").toString();
        params.remove("email");
        mailService.sendNoticeMail(email, params);
        return "SUCCESS";
    }

}
