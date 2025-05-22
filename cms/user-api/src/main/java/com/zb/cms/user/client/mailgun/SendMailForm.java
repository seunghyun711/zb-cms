package com.zb.cms.user.client.mailgun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SendMailForm {
    private String from;
    private String to;
    private String subject;
    private String text;

}
