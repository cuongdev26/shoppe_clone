package com.shoppe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_variants")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Products product;

    @Column(name = "variant_name", nullable = false, length = 255)
    String variantName;

    @Column(nullable = false, precision = 15, scale = 2)
    BigDecimal price;

    @Column(name = "stock_quantity")
    int stockQuantity;

    // ✅ FIX: SKU nên unique
    @Column(length = 100, unique = true)
    String sku;

    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}