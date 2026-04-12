package com.shoppe.controller;

import com.shoppe.constant.ProductStatus;
import com.shoppe.dto.request.SearchProductRequest;
import com.shoppe.dto.response.ApiResponse;
import com.shoppe.dto.response.PageResponse;
import com.shoppe.dto.response.SearchProductResponse;
import com.shoppe.service.impl.SearchProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchProductController {

    private final SearchProductServiceImpl searchProductServiceimpl;

    @GetMapping
    public ApiResponse<PageResponse<SearchProductResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        SearchProductRequest request = SearchProductRequest.builder()
                .keyword(keyword)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .status(status != null ? ProductStatus.valueOf(status) : null)
                .shopId(shopId)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDir)
                .build();

        return ApiResponse.<PageResponse<SearchProductResponse>>builder()
                .result(searchProductServiceimpl.searchProducts(request).getResult())
                .build();
    }
}