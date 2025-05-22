package com.zb.cms.user.domain.seller;

import com.zb.cms.user.domain.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDto {

    private Long id;
    private String email;

    public static SellerDto from(Seller seller) {
        return new SellerDto(seller.getId(), seller.getEmail());
    }
}
