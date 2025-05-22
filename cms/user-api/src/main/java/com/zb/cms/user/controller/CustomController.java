package com.zb.cms.user.controller;

import com.zb.cms.user.domain.customer.ChangeBalanceForm;
import com.zb.cms.user.domain.customer.CustomerDto;
import com.zb.cms.user.domain.model.Customer;
import com.zb.cms.user.exception.CustomException;
import com.zb.cms.user.exception.ErrorCode;
import com.zb.cms.user.service.customer.CustomerBalanceService;
import com.zb.cms.user.service.customer.CustomerService;
import com.zb.doamin.common.UserVo;
import com.zb.doamin.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;

    //고객 본인의 아이디, 이메일, 잔액 확인
    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {

        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        Customer c = customerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(CustomerDto.from(c));
    }

    //계좌 잔액 변경
    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody ChangeBalanceForm form) {

        UserVo vo = jwtAuthenticationProvider.getUserVo(token);

        return ResponseEntity.ok(
                customerBalanceService.changeBalance(vo.getId(), form).getCurrentMoney());

    }
}
