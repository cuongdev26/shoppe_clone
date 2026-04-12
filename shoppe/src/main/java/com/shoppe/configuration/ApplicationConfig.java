package com.shoppe.configuration;

import com.shoppe.constant.Role;
import com.shoppe.entity.Customer;
import com.shoppe.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationConfig {

    private final PasswordEncoder passwordEncoder;

    public ApplicationConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    ApplicationRunner applicationRunner(CustomerRepository userReponsitory) {
        return args -> {
            if(userReponsitory.findByEmail("phamcuong26.dev@gmail.com").isEmpty()) {
                HashSet<Role> roles = new HashSet<>();
                roles.add(Role.ADMIN);

                Customer customer = Customer.builder()
                        .name("ADMIN")
                        .roles(roles)
                        .email("phamcuong26.dev@gmail.com")
                        .password(passwordEncoder.encode("Admin2626"))
                        .build();
                userReponsitory.save(customer);
                log.warn("Admin user has been created");
            }
        };
    }

}
