package com.zb.cms.user.service;

import com.zb.cms.user.client.MailgunClient;
import com.zb.cms.user.client.mailgun.SendMailForm;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public Response sendEmail() {
        SendMailForm form = SendMailForm.builder()
                .from("zb-test.my.com")
                .to("edu.hong.gmail.com")
                .subject("test email from zb")
                .build();
        return mailgunClient.sendEmail(form);
    }
}
