package com.shoppe.entity;

import com.shoppe.constant.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(name = "password", nullable = false, length = 255)
    String password;

    @Column(nullable = false, unique = true)
    String email;

    @Column(name = "phone", length = 20)
    String phoneNo;

    LocalDate dob;

    @Column(name = "avatar_url")
    String avatarUrl;

    @Column(name = "is_deleted")
    boolean deleted = false;

    @Column(name = "create_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)  // ✅ đổi thành EAGER
    @CollectionTable(
            name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id")  // ✅ thêm dòng này
    )
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Set<Role> roles;

    @PrePersist
    protected void onCreated() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdated() {
        updatedAt = LocalDateTime.now();
    }
}