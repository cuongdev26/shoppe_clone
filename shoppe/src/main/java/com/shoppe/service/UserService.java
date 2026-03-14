package com.shoppe.service;

import com.shoppe.dto.request.UserCreationRequest;
import com.shoppe.dto.request.UserUpdateRequest;
import com.shoppe.dto.response.UserResponse;
import com.shoppe.entity.Customer;

import java.util.List;

public interface UserService {

    UserResponse creationCustomer(UserCreationRequest request);
    List<UserResponse> getCustomer();
    UserResponse getCustomerEmail(String email);
    UserResponse updateCustomer(UserUpdateRequest request);
    void deleteCustomer(String email);
}
