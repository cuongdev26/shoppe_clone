package com.shoppe.service;

import com.shoppe.dto.request.AuthenticationRequest;
import com.shoppe.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String generateToken(String email);
}
