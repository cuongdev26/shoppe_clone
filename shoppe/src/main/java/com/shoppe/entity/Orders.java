package com.shoppe.entity;

import com.shoppe.constant.PaymentMethodOrders;
import com.shoppe.constant.PaymentStatusOrders;
import com.shoppe.constant.StatusOrders;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "order_code", nullable = false, unique = true, length = 50)
    String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Column(name = "recipient_name", nullable = false, length = 255)
    String recipientName;

    @Size(min = 8)
    @Column(name = "recipient_phone", nullable = false, length = 20)
    String recipientPhone;

    @Column(name = "shipping_address", nullable = false, length = 500)
    String shippingAddress;

    @Column(name = "shipping_ward", length = 100)
    String shippingWard;

    @Column(name = "shipping_district", length = 100)
    String shippingDistrict;

    @Column(name = "shipping_city", nullable = false, length = 100)
    String shippingCity;

    @Column(nullable = false, precision = 15, scale = 2)
    BigDecimal subtotal;

    @Column(name = "shipping_fee", precision = 15, scale = 2)
    BigDecimal shippingFee;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    BigDecimal totalAmount;

    @Column(nullable = false, name = "payment_method")
    @Enumerated(EnumType.STRING)
    PaymentMethodOrders paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    PaymentStatusOrders paymentStatus;

    @Column(name = "paid_at")
    LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    StatusOrders status;

    @Column(columnDefinition = "TEXT")
    String note;

    @Column(name = "cancel_reason", columnDefinition = "TEXT")
    String cancelReason;

    @Column(name = "confirmed_at")
    LocalDateTime confirmedAt;

    @Column(name = "shipping_at")
    LocalDateTime shippingAt;

    @Column(name = "delivered_at")
    LocalDateTime deliveredAt;

    @Column(name = "completed_at")
    LocalDateTime completedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<OrderItems> items = new ArrayList<>();

    @Column(name = "cancelled_at")
    LocalDateTime cancelledAt;

    // ✅ FIX: "created_At" → "created_at" (consistent naming)
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    // ✅ FIX: Thiếu @PrePersist và @PreUpdate
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusOrders.pending;
        }
        if (this.paymentStatus == null) {
            this.paymentStatus = PaymentStatusOrders.pending;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}