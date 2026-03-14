package com.shoppe.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSearchRequest {

    // Tìm theo tên hoặc mô tả
    private String keyword;

    // Lọc theo shop
    private Integer shopId;

    // Lọc theo khoảng giá
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    // Lọc theo rating tối thiểu của shop
    private BigDecimal minRating;

    // Lọc theo trạng thái: Active, inactive, outOfStock
    private String status;

    // Sắp xếp: newest | price_asc | price_desc | rating_desc
    private String sortBy = "newest";

    // Phân trang
    private int page = 0;
    private int size = 20;
}