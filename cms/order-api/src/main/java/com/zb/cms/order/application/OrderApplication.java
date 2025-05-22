package com.zb.cms.order.application;

import com.zb.cms.order.client.UserClient;
import com.zb.cms.order.client.user.ChangeBalanceForm;
import com.zb.cms.order.domain.model.ProductItem;
import com.zb.cms.order.domain.redis.Cart;
import com.zb.cms.order.exception.CustomException;
import com.zb.cms.order.exception.ErrorCode;
import com.zb.cms.order.service.ProductItemService;
import com.zb.cms.user.domain.customer.CustomerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderApplication {

    private final CartApplication cartApplication;
    private final UserClient userClient;
    private final ProductItemService productItemService;

    @Transactional
    public void order(String token, Cart cart) {
        Cart orderCart = cartApplication.refreshCart(cart);
        if (!orderCart.getMessages().isEmpty()) {
            throw new CustomException(ErrorCode.ORDER_FAIL_CHECK_CART);
        }
        CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();

        int totalPrice = getTotalPrice(cart);

        if (customerDto.getBalance() < totalPrice) {
            throw new CustomException(ErrorCode.ORDER_FAIL_NO_MONEY);
        }

        userClient.changeBalance(token, ChangeBalanceForm.builder()
                .from("USER")
                .money(-totalPrice)
                .message("Order")
                .build());

        for (Cart.Product product : orderCart.getProducts()) {
            for (Cart.ProductItem cartItem : product.getItems()) {
                ProductItem productItem = productItemService.getProductItem(cartItem.getId());
                productItem.setCount(productItem.getCount() - cartItem.getCount());
            }
        }
    }

    public Integer getTotalPrice(Cart cart) {
        int sum = 0;
        for (Cart.Product p : cart.getProducts()) {
            sum += p.getItems().stream().mapToInt(Cart.ProductItem::getPrice).sum();
        }
        return sum;

    }
}
