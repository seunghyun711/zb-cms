package com.zb.cms.order.service;

import com.zb.cms.order.domain.model.Product;
import com.zb.cms.order.domain.model.ProductItem;
import com.zb.cms.order.domain.product.AddProductItemForm;
import com.zb.cms.order.domain.product.UpdateProductItemForm;
import com.zb.cms.order.domain.repository.ProductItemRepository;
import com.zb.cms.order.domain.repository.ProductRepository;
import com.zb.cms.order.exception.CustomException;
import com.zb.cms.order.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductItemService {

    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;

    @Transactional
    public ProductItem getProductItem(Long id) {
        return productItemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_ITEM_NOT_FOUND));
    }

    @Transactional
    public Product addProductItem(Long sellerId, AddProductItemForm form) {
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        if (product.getProductItems().stream()
                .anyMatch(item -> item.getName().equals(form.getName()))
        ) {
            throw new CustomException(ErrorCode.EXIST_PRODUCT_ITEM);
        }

        ProductItem productItem = ProductItem.of(sellerId, form);
        product.getProductItems().add(productItem);
        return product;
    }

    @Transactional
    public ProductItem updateProductItem(Long sellerId, UpdateProductItemForm form) {

        ProductItem productItem = productItemRepository.findById(form.getId())
                .filter(pi -> pi.getSellerId().equals(sellerId))
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_ITEM_NOT_FOUND));

        productItem.setName(form.getName());
        productItem.setCount(form.getCount());
        productItem.setPrice(form.getPrice());
        return productItem;
    }

    @Transactional
    public void deleteProductItem(Long sellerId, Long productItemId) {
        ProductItem productItem = productItemRepository.findById(productItemId)
                .filter(pi -> pi.getSellerId().equals(sellerId))
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_ITEM_NOT_FOUND));

        productItemRepository.delete(productItem);
    }
}
