package com.zb.cms.user.service;

import com.zb.cms.user.domain.SignUpForm;
import com.zb.cms.user.service.customer.SignUpCustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    void signUp() {
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("abc@gmail.com")
                .password("1")
                .phone("19199229")
                .build();
        assertNotNull(service.signUp(form).getId());
    }

}