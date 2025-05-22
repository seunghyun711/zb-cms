package com.zb.cms.user.application;

import com.zb.cms.user.domain.SignInForm;
import com.zb.cms.user.domain.model.Customer;
import com.zb.cms.user.domain.model.Seller;
import com.zb.cms.user.exception.CustomException;
import com.zb.cms.user.exception.ErrorCode;
import com.zb.cms.user.service.customer.CustomerService;
import com.zb.cms.user.service.seller.SellerService;
import com.zb.doamin.common.UserType;
import com.zb.doamin.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));

        // 2. 토큰 발행


        // 3. 토큰 response
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form) {
        Seller c = sellerService.findValidSeller(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.SIGN_IN_ERROR));

        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }
}
