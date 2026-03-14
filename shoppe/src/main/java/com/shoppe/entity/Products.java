package com.shoppe.entity;

import com.shoppe.constant.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    // ✅ FIX: @JoinColumn name="" rỗng → phải điền "shop_id"
    // ✅ FIX: Đổi tên field từ shopId → shop (đúng convention với @ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    Shops shop;

    @Column(nullable = false, length = 500)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(precision = 15, scale = 2, nullable = false)
    BigDecimal price;

    @Column(name = "stock_quantity")
    int stockQuantity;

    @Column(name = "image_url", length = 500)
    String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    ProductStatus status;

    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreated() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ProductStatus.Active;
        }
    }

    @PreUpdate
    protected void onUpdated() {
        updatedAt = LocalDateTime.now();
    }
}