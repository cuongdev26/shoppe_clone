package com.shoppe.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.shoppe.dto.request.AuthenticationRequest;
import com.shoppe.dto.response.AuthenticationResponse;
import com.shoppe.entity.Customer;
import com.shoppe.exception.AppException;
import com.shoppe.exception.ErrorCode;
import com.shoppe.properties.RsaKeyConfigProperties;
import com.shoppe.repository.CustomerRepository;
import com.shoppe.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceimpl implements AuthenticationService {

   CustomerRepository customerRepository;
   RsaKeyConfigProperties rsaKeyConfigProperties;
   PasswordEncoder passwordEncoder;

   // tìm user xem có tồ tại hay kh ông
    @Override
   public AuthenticationResponse authenticate(AuthenticationRequest request) {
       Customer customer = customerRepository.findByEmail(request.getEmail())
               .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

       // encode password và so sánh
       boolean authenticated = passwordEncoder.matches(
               request.getPassword(),
               customer.getPassword()
       );

       // nếu k phải trả về lỗi 
       if(!authenticated)
           throw new AppException(ErrorCode.UNAUTHENTICATED);

       String token = generateToken(customer.getEmail());

       return AuthenticationResponse.builder()
               .token(token)
               .authenticated(true)
               .build();
   }

   @Override
   public String generateToken(String email) {
       JWSHeader header = new JWSHeader(JWSAlgorithm.RS512);
       JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
               .subject(email)
               .issuer("phamcuongdev")
               .issueTime(new Date())
               .expirationTime(
               Date.from(Instant.now().plus(1, ChronoUnit.HOURS))
               )
               .build();

       Payload payload = new Payload(claimsSet.toJSONObject());
       JWSObject jwsObject = new JWSObject(header,payload);

       try{
           jwsObject.sign(new RSASSASigner(rsaKeyConfigProperties.getPrivateKey()));
           return jwsObject.serialize();
       }catch (JOSEException e) {
           throw  new RuntimeException("không thể tạo token " ,e);
       }
   }
}
