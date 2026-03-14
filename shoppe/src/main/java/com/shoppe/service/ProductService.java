package com.shoppe.service;

import com.shoppe.dto.request.ProductSearchRequest;
import com.shoppe.dto.response.ApiResponse;
import com.shoppe.dto.response.ProductSearchResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductSearchResponse> searchProducts(ProductSearchRequest request);
}