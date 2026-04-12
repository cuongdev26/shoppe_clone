package com.shoppe.dto.request;

import com.shoppe.constant.ProductStatus;
import com.shoppe.constant.StatusOrders;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchProductRequest {

    String keyword;
    Integer shopId;
    ProductStatus status;
    BigDecimal minPrice;
    BigDecimal maxPrice;

    int page = 0;
    int size = 10;
    String sortBy = "createdAt"; // sắp xép thơi gian ra sản phẩm
    String sortDirection = "desc"; // sắp xếp giảm dần

}
