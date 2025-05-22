package com.zb.cms.user.application;

import com.zb.cms.user.client.MailgunClient;
import com.zb.cms.user.client.mailgun.SendMailForm;
import com.zb.cms.user.domain.SignUpForm;
import com.zb.cms.user.domain.model.Customer;
import com.zb.cms.user.exception.CustomException;
import com.zb.cms.user.exception.ErrorCode;
import com.zb.cms.user.service.customer.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExists(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }else{
            Customer c = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("seunghyun711@naver.com")
                    .to(form.getEmail())
                    .subject("Verification Email")
                    .text(getVerificationEmailBody(form.getEmail(), form.getName(), code))
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);

            return "회원 가입 성공";
        }



    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String code) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ").append(name).append("! Please click linke for verification! \n\n")
                .append("http://localhost:8080/customer/signup/verify?email=")
                .append("email")
                .append("&code=")
                .append(code).toString();
    }


}
