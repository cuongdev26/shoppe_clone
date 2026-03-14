package com.shoppe.dto.response;

import com.shoppe.constant.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String name;
    String email;
    String phone;
    String avatarUrl;
    LocalDate dob;
    Set<Role> roles;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}