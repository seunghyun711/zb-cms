package com.zb.cms.order.client.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerDto {

    private Long id;

    private String email;

    private Integer balance;
}
