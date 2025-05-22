package com.zb.cms.order.domain.repository;

import com.zb.cms.order.domain.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    // test 시 product를 조회할때 productItems 도 함께 조회하도록 설정
    @EntityGraph(attributePaths = {"productItems"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findBySellerIdAndId(Long sellerId, Long id);

    @EntityGraph(attributePaths = {"productItems"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findWithProductItemsById(long id);

}
