package com.zb.cms.order.controller;

import com.zb.cms.order.application.CartApplication;
import com.zb.cms.order.application.OrderApplication;
import com.zb.cms.order.domain.product.AddProductCartForm;
import com.zb.cms.order.domain.redis.Cart;
import com.zb.doamin.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/cart")
public class CustomerCartController {

    private final CartApplication cartApplication;
    private final JwtAuthenticationProvider provider;
    private final OrderApplication orderApplication;

    //카트에 product item List(여러 옵션들) 담기
    @PostMapping
    public ResponseEntity<Cart> addCart(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody AddProductCartForm form) {
        return ResponseEntity.ok(cartApplication.addCart(provider.getUserVo(token).getId(), form));
    }

    //카트 확인
    @GetMapping
    public ResponseEntity<Cart> showCart(
            @RequestHeader(name = "X-AUTH-TOKEN") String token) {
        return ResponseEntity.ok(cartApplication.getCart(provider.getUserVo(token).getId()));
    }

    //고객의 카트를 새로운 것으로 교체
    @PutMapping
    public ResponseEntity<Cart> updateCart(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody Cart cart) {
        return ResponseEntity.ok(cartApplication.updateCart(provider.getUserVo(token).getId(), cart));
    }

    //카트에 담긴 물품 주문하기(계산)
    @PostMapping("/order")
    public ResponseEntity<Cart> order(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody Cart cart) {
        orderApplication.order(token, cart);
        return ResponseEntity.ok().build();
    }
}
