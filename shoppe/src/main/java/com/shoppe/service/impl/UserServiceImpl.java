package com.shoppe.service.impl;

import com.shoppe.constant.Role;
import com.shoppe.dto.request.UserCreationRequest;
import com.shoppe.dto.request.UserUpdateRequest;
import com.shoppe.dto.response.UserResponse;
import com.shoppe.entity.Customer;
import com.shoppe.exception.AppException;
import com.shoppe.exception.ErrorCode;
import com.shoppe.repository.UserRepository;
import com.shoppe.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userReponsitory, PasswordEncoder passwordEncoder) {
        this.userRepository = userReponsitory;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse mapToUserResponse(Customer customer) {
        return UserResponse.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhoneNo())
                .avatarUrl(customer.getAvatarUrl())
                .dob(customer.getDob())
                .roles(customer.getRoles())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .id(customer.getId())
                .build();
    }

    @Override
    public UserResponse creationCustomer(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email exits " + request.getEmail());
        }

        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        Customer customer = Customer.builder()
                .name(request.getName())
                .dob(request.getDob())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNo(request.getPhone())
                .roles(roles)
                .build();

        Customer saved = userRepository.save(customer);
        return mapToUserResponse(saved);
    }

    @Override
    public List<UserResponse> getCustomer() {
        return userRepository.findAll()
                .stream()
                .map(customer -> UserResponse.builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail())
                        .phone(customer.getPhoneNo())
                        .avatarUrl(customer.getAvatarUrl())
                        .dob(customer.getDob())
                        .createdAt(customer.getCreatedAt())
                        .roles(customer.getRoles())
                        .updatedAt(customer.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public UserResponse  getCustomerEmail(String email) {
        Customer customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("email have not exit"));
        return mapToUserResponse(customer);

    }

    @Override
    public UserResponse updateCustomer(UserUpdateRequest request) {
        Customer customer = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not exits"));
        customer.setName(request.getName());
        customer.setDob(request.getDob());
        customer.setAvatarUrl(request.getAvatarUrl());
        customer.setPassword(request.getPassword());
        customer.setPhoneNo(request.getPhone());
        customer.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return mapToUserResponse(userRepository.save(customer));
    }

    @Override
    public void deleteCustomer(String id) {
        log.info("Deleting customer id: {}", id);

        Customer customer = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // ✅ FIX: Soft delete - đánh dấu deleted = true
        // Không xóa thật khỏi DB, giữ lại dữ liệu lịch sử
        customer.setDeleted(true);
        userRepository.save(customer);
    }
}
