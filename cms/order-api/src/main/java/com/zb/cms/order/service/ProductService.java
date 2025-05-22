package com.zb.cms.order.service;

import com.zb.cms.order.domain.model.Product;
import com.zb.cms.order.domain.model.ProductItem;
import com.zb.cms.order.domain.product.AddProductForm;
import com.zb.cms.order.domain.product.UpdateProductForm;
import com.zb.cms.order.domain.product.UpdateProductItemForm;
import com.zb.cms.order.domain.repository.ProductRepository;
import com.zb.cms.order.exception.CustomException;
import com.zb.cms.order.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product addProduct(Long sellerId, AddProductForm form) {
        return productRepository.save(Product.of(sellerId, form));
    }

    @Transactional
    public Product updateProduct(Long sellerId, UpdateProductForm form) {
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setName(form.getName());
        product.setDescription(form.getDescription());

        //폼의 옵션 리스트를 돌면서 기존 상품의 옵션들을 찾아 가격이나 수량 등을 변경해준다.
        for (UpdateProductItemForm itemForm : form.getItems()) {
            ProductItem item = product.getProductItems().stream()
                    .filter(pi -> pi.getId().equals(itemForm.getId()))
                    .findFirst().orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_ITEM_NOT_FOUND));

            item.setName(itemForm.getName());
            item.setCount(itemForm.getCount());
            item.setPrice(itemForm.getPrice());
        }
        return product;
    }

    @Transactional
    public void deleteProduct(Long sellerId, Long productId) {
        Product product = productRepository.findBySellerIdAndId(sellerId, productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
