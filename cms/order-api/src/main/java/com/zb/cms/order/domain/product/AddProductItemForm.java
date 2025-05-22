package com.zb.cms.order.domain.product;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductItemForm {
    private Long productId;

    private String name;

    private Integer price;

    private Integer count;
}
