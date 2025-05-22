package com.zb.cms.user.client.service;

import com.zb.cms.user.client.MailgunClient;
import com.zb.cms.user.service.EmailSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSendServiceTest {
    @Autowired
    private MailgunClient mailgunClient;

    @Autowired
    private EmailSendService emailSendService;

    @Test
    public void EmailTest() {
        String response = emailSendService.sendEmail().toString();
        System.out.println("response : " + response);

    }

}