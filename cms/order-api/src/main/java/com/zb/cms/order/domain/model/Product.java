package com.zb.cms.order.domain.model;

import com.zb.cms.order.domain.product.AddProductForm;
import com.zb.cms.user.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
@Audited
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductItem> productItems = new ArrayList<>();

    public static Product of(Long sellerId, AddProductForm form) {
        return Product.builder()
                .sellerId(sellerId)
                .name(form.getName())
                .description(form.getDescription())
                .productItems(form.getItems().stream()
                        .map(pi -> ProductItem.of(sellerId, pi)).toList())
                .build();
    }
}
