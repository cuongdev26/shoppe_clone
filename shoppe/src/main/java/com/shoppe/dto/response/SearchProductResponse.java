package com.shoppe.dto.response;

import com.shoppe.constant.ProductStatus;
import com.shoppe.constant.StatusOrders;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchProductResponse {
    int id;
    String description; // ghi chú
    int stockQuantity; // số lượng tồn kho
    String imageUrl;
    BigDecimal price;
    String name;
    ProductStatus status;
    int page;
    String shopName;
    int shopId;
    int totalOrders; // tổng số đơn đã bán
    LocalDateTime createdAt;
    LocalDateTime updatedAt;


}
