package com.zb.cms.order.domain.repository;

import com.zb.cms.order.domain.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    // ProductRepositoryCustom과  구현체를 만들어 searchByName(이름으로 상품 검색) 메서드를 추가하고 기존 ProductRepository 가 상속하도록 해서 붙여준다.
    List<Product> searchByName(String name);
}
