package com.zb.cms.order.controller;

import com.zb.cms.order.domain.product.ProductDto;
import com.zb.cms.order.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/product")
@RequiredArgsConstructor
public class SearchController {

    private final ProductSearchService productSearchService;

    // 상품 검색
    @GetMapping
    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(
                productSearchService.searchByName(name).stream()
                        .map(ProductDto::withoutItemsFrom).toList()
        );
    }

    // 상품 상세정보 조회
    @GetMapping("/detail")
    public ResponseEntity<ProductDto> getDetail(@RequestParam Long productId) {
        return ResponseEntity.ok(
                ProductDto.from(productSearchService.getByProductId(productId))
        );
    }
}
