package com.shoppe.service.impl;

import com.shoppe.dto.request.ProductSearchRequest;
import com.shoppe.dto.response.ProductSearchResponse;
import com.shoppe.entity.Products;
import com.shoppe.repository.ProductRepository;
import com.shoppe.service.ProductService;
import com.shoppe.specification.ProductSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Override
    public Page<ProductSearchResponse> searchProducts(ProductSearchRequest request) {

        // Giới hạn size tối đa 50 tránh quá tải
        int size = Math.min(request.getSize(), 50);
        Pageable pageable = PageRequest.of(request.getPage(), size);

        // Build specification từ request
        Specification<Products> spec = ProductSpecification.buildSpec(request);

        // Query DB
        Page<Products> productsPage = productRepository.findAll(spec, pageable);

        // Map entity → response DTO
        return productsPage.map(this::mapToResponse);
    }

    private ProductSearchResponse mapToResponse(Products product) {
        return ProductSearchResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .status(product.getStatus() != null ? product.getStatus().name() : null)
                // Thông tin shop (nếu có)
                .shopId(product.getShop() != null ? product.getShop().getId() : null)
                .shopName(product.getShop() != null ? product.getShop().getShopName() : null)
                .shopAvatar(product.getShop() != null ? product.getShop().getShopAvatar() : null)
                .shopRating(product.getShop() != null ? product.getShop().getRatingAverage() : null)
                .createdAt(product.getCreatedAt())
                .build();
    }
}