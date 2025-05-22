package com.zb.cms.order.domain.redis;

import com.zb.cms.order.domain.product.AddProductCartForm;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash("cart")
public class Cart {

    @Id
    private Long customerId;
    private List<Product> products = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    //실제 물건에 변경사항이 있을때 카트 확인시 고객에게 알려줄 메시지들
    public void addMessage(String message) {
        messages.add(message);
    }

    //  product 내 여러 ProductItem 포함
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Product {

        private Long id;
        private Long sellerId;
        private String name;
        private String description;
        private List<ProductItem> items = new ArrayList<>();

        public static Product from(AddProductCartForm form) {
            return Product.builder()
                    .id(form.getId())
                    .sellerId(form.getSellerId())
                    .name(form.getName())
                    .description(form.getDescription())
                    .items(form.getItems().stream()
                            .map(ProductItem::from).toList())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductItem {

        private Long id;
        private String name;
        private Integer count;
        private Integer price;

        public static ProductItem from(AddProductCartForm.ProductItem form) {
            return ProductItem.builder()
                    .id(form.getId())
                    .name(form.getName())
                    .count(form.getCount())
                    .price(form.getPrice())
                    .build();
        }
    }
}
