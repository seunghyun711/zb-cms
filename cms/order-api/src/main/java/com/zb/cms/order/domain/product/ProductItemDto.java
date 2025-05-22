package com.zb.cms.order.domain.product;

import com.zb.cms.order.domain.model.ProductItem;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemDto {

    private Long id;

    private String name;

    private Integer price;

    private Integer count;

    public static ProductItemDto from(ProductItem item) {
        return new ProductItemDto(item.getId(), item.getName(), item.getPrice(), item.getCount());
    }
}
