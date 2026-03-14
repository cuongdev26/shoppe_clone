package com.shoppe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchResponse {

    // Thông tin sản phẩm
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private String status;

    // Thông tin shop
    private Integer shopId;
    private String shopName;
    private String shopAvatar;
    private BigDecimal shopRating;

    private LocalDateTime createdAt;
}