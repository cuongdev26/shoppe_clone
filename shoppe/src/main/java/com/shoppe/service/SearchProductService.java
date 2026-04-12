package com.shoppe.service;

import com.shoppe.dto.request.SearchProductRequest;
import com.shoppe.dto.response.ApiResponse;
import com.shoppe.dto.response.PageResponse;
import com.shoppe.dto.response.SearchProductResponse;

public interface SearchProductService {
    ApiResponse<PageResponse<SearchProductResponse>> searchProducts(SearchProductRequest request);
}
