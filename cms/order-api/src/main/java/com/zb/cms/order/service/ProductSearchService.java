package com.zb.cms.order.service;

import com.zb.cms.order.domain.model.Product;
import com.zb.cms.order.domain.repository.ProductRepository;
import com.zb.cms.order.exception.CustomException;
import com.zb.cms.order.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository productRepository;

    public List<Product> searchByName(String name) {
        return productRepository.searchByName(name);
    }

    public Product getByProductId(Long productId) {
        return productRepository.findWithProductItemsById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public List<Product> getListByProductIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }
}
