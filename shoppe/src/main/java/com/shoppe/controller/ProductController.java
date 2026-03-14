package com.shoppe.controller;

import com.shoppe.dto.request.ProductSearchRequest;
import com.shoppe.dto.response.ApiResponse;
import com.shoppe.dto.response.ProductSearchResponse;
import com.shoppe.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shoppe/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    /**
     * Tìm kiếm & lọc sản phẩm nâng cao
     *
     * GET /shoppe/products/search
     *
     * Ví dụ:
     *   /shoppe/products/search?keyword=áo&minPrice=50000&maxPrice=500000&sortBy=price_asc
     *   /shoppe/products/search?shopId=1&minRating=4.0&sortBy=rating_desc
     *   /shoppe/products/search?keyword=giày&page=0&size=10
     */
    @GetMapping("/search")
    ApiResponse<Page<ProductSearchResponse>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer shopId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "newest") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        // Build request object
        ProductSearchRequest request = new ProductSearchRequest();
        request.setKeyword(keyword);
        request.setShopId(shopId);
        request.setMinPrice(minPrice);
        request.setMaxPrice(maxPrice);
        request.setMinRating(minRating);
        request.setStatus(status);
        request.setSortBy(sortBy);
        request.setPage(page);
        request.setSize(size);

        log.info("Search products: keyword={}, shopId={}, sortBy={}, page={}", keyword, shopId, sortBy, page);

        return ApiResponse.<Page<ProductSearchResponse>>builder()
                .result(productService.searchProducts(request))
                .build();
    }
}