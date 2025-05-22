package com.zb.cms.order.domain.product;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductForm {

    private String name;

    private String description;

    private List<AddProductItemForm> items;
}
