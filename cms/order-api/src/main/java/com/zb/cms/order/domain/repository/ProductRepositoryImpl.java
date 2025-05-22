package com.zb.cms.order.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zb.cms.order.domain.model.Product;
import com.zb.cms.order.querydsl.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory jf;

    @Override
    public List<Product> searchByName(String name) {
        String search = "%" + name + "%";

        QProduct product = QProduct.product;
        return jf.selectFrom(product)
                .where(product.name.like(search))
                .fetch();
    }
}
