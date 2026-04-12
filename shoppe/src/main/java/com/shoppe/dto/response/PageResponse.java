package com.shoppe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {

    List<T> content; //  danh sách sản phẩm trong trang hiện tại(data)
    int pageNo; // trang hiện tại
    int pageSize; // số item mõi trang
    long totalElements; // tổng số item
    int totalPages; // tổng số trang
    boolean lastPage; // có phải trang cuối cùng hay không
}
