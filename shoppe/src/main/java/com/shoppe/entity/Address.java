package com.shoppe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id",insertable = false,updatable = false)
        Customer customer;

        @Column(name = "recipient_name",length = 255,nullable = false)
        String recipientName; // tên người nhận

        @Column(nullable = false, length = 20)
        String phone;

        @Column(name = "address_line",nullable = false,length = 500)
        String addressLine; // địa chỉ chi tiết

        @Column(nullable = false, length = 100)
        String ward; // phường xã

        @Column(nullable = false, length = 100)
        String district; // quận huyện

        @Column(nullable = false,length = 100)
        String city;

        @Column(name = "is_default")
        Boolean isDefault;

        @Column(name = "create_at")
        LocalDateTime createdAt;

        @Column(name = "update_at")
        LocalDateTime updatedAt;

        @PrePersist
        protected void onCreate() {
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
            // Set default value nếu chưa có
            if (this.isDefault == null) {
                this.isDefault = false;
            }
        }

        @PreUpdate
        protected void onUpdate() {
            this.updatedAt = LocalDateTime.now();
        }
    }

