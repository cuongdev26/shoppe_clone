package com.shoppe.repository;

import com.shoppe.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer>,
        JpaSpecificationExecutor<Products> {
    // JpaSpecificationExecutor cho phép dùng specification để filter động
}