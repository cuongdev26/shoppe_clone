package com.shoppe.service.impl;

import com.shoppe.constant.ProductStatus;
import com.shoppe.dto.request.SearchProductRequest;
import com.shoppe.dto.response.ApiResponse;
import com.shoppe.dto.response.PageResponse;
import com.shoppe.dto.response.SearchProductResponse;
import com.shoppe.repository.SearchProductRepository;
import com.shoppe.service.SearchProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchProductServiceImpl implements SearchProductService {

    SearchProductRepository searchProductRepository;

    @Override
    public ApiResponse<PageResponse<SearchProductResponse>> searchProducts(SearchProductRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Object[]> rows = searchProductRepository.searchProducts(
                request.getShopId(),
                request.getKeyword(),
                request.getStatus() != null ? ProductStatus.valueOf(request.getStatus().name()) : null,
                request.getMinPrice(),
                request.getMaxPrice(),
                request.getSortBy(),
                request.getSortDirection(),
                pageable
        );

        return ApiResponse.<PageResponse<SearchProductResponse>>builder()
                .result(PageResponse.<SearchProductResponse>builder()
                        .content(rows.map(this::mapToResponse).getContent())
                        .pageNo(rows.getNumber())
                        .pageSize(rows.getSize())
                        .totalElements(rows.getTotalElements())
                        .totalPages(rows.getTotalPages())
                        .lastPage(rows.isLast())
                        .build())
                .build();
    }

    private SearchProductResponse mapToResponse(Object[] row) {
        return SearchProductResponse.builder()
                .id(row[0] != null ? ((Number) row[0]).intValue() : null)
                .name((String) row[1])
                .build();
    }
}