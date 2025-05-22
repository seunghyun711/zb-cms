package com.zb.cms.order.domain.model;

import com.zb.cms.order.domain.product.AddProductItemForm;
import com.zb.cms.user.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
@Audited
@Entity
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;

    @Audited
    private String name;

    @Audited
    private Integer price;

    private Integer count;

    @ManyToOne
    private Product product;

    public static ProductItem of(Long sellerId, AddProductItemForm form) {
        return ProductItem.builder()
                .sellerId(sellerId)
                .name(form.getName())
                .price(form.getPrice())
                .count(form.getCount())
                .build();
    }
}
