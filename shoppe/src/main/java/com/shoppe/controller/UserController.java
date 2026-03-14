package com.shoppe.controller;

import com.shoppe.dto.request.UserCreationRequest;
import com.shoppe.dto.request.UserUpdateRequest;
import com.shoppe.dto.response.ApiResponse;
import com.shoppe.dto.response.UserResponse;
import com.shoppe.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shoppe/customer")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class UserController {
    UserService userService;

    @GetMapping
    ApiResponse<List<UserResponse>> getCustomer() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getCustomer())
                .build();
    }

    @GetMapping("/{email}")
    ApiResponse<UserResponse> getCustomerId(@PathVariable("email") String email) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getCustomerEmail(email))
                .build();
    }

    @PostMapping("/create")
    ApiResponse<UserResponse> creationCustomer(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.creationCustomer(request))
                .build();
    }

    @PutMapping("/update")
    ApiResponse<UserResponse> updateCustomer(@RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse> builder()
                .result(userService.updateCustomer(request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteCustomer(@PathVariable String id){
        userService.deleteCustomer(id);

        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();

    }
}

