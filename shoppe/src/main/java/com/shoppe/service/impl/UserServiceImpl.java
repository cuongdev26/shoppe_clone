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

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
        log.info("=== [creationCustomer] START - email: {}, name: {}",
                request.getEmail(), request.getName());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("[creationCustomer] Email đã tồn tại: {}", request.getEmail());
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
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
        log.info("[creationCustomer] SUCCESS - id: {}, email: {}",
                saved.getId(), saved.getEmail());
        return mapToUserResponse(saved);
    }

    @Override
    public List<UserResponse> getCustomer() {
        log.info("=== [getCustomer] START - lấy danh sách tất cả customer");

        List<Customer> customers = userRepository.findAll();
        log.info("[getCustomer] Tìm thấy {} customer", customers.size());

        return customers.stream()
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
    public UserResponse getCustomerEmail(String email) {
        log.info("=== [getCustomerEmail] START - email: {}", email);

        Customer customer = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("[getCustomerEmail] Không tìm thấy email: {}", email);
                    return new AppException(ErrorCode.USER_NOT_FOUND); // nên dùng AppException thay RuntimeException
                });

        log.info("[getCustomerEmail] SUCCESS - id: {}", customer.getId());
        return mapToUserResponse(customer);
    }

    @Override
    public UserResponse updateCustomer(UserUpdateRequest request) {
        log.info("=== [updateCustomer] START - email: {}", request.getEmail());

        Customer customer = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("[updateCustomer] Không tìm thấy user với email: {}", request.getEmail());
                    return new AppException(ErrorCode.USER_NOT_FOUND);
                });

        log.debug("[updateCustomer] Trước update - name: {}, phone: {}",
                customer.getName(), customer.getPhoneNo());

        customer.setName(request.getName());
        customer.setDob(request.getDob());
        customer.setAvatarUrl(request.getAvatarUrl());
        customer.setPhoneNo(request.getPhone());
        customer.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            log.debug("[updateCustomer] Đổi password cho email: {}", request.getEmail());
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Customer saved = userRepository.save(customer);
        log.info("[updateCustomer] SUCCESS - id: {}", saved.getId());
        return mapToUserResponse(saved);
    }

    @Override
    public void deleteCustomer(String id) {
        log.info("=== [deleteCustomer] START - id: {}", id);

        Customer customer = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[deleteCustomer] Không tìm thấy customer id: {}", id);
                    return new AppException(ErrorCode.USER_NOT_FOUND);
                });

        customer.setDeleted(true);
        userRepository.save(customer);
        log.info("[deleteCustomer] SUCCESS (soft delete) - id: {}, email: {}",
                id, customer.getEmail());
    }
}