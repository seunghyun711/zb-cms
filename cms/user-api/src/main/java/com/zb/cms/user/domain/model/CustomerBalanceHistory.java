package com.zb.cms.user.domain.model;

import com.zb.cms.user.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CustomerBalanceHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    private Customer customer;

    private Integer changeMoney;
    private Integer currentMoney;
    private String fromMessage;
    private String description;
}
