package com.shoppe.repository;

import com.shoppe.constant.ProductStatus;
import com.shoppe.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface SearchProductRepository extends JpaRepository<Products, Integer> {

    @Query(value = """
        SELECT p.id, p.shop_id, p.name, p.description, p.price,
               p.stock_quantity, p.image_url, p.status,
               p.created_at, p.updated_at,
               s.shop_name,
               COUNT(oi.id) as total_orders
        FROM products p
        JOIN shops s ON p.shop_id = s.id
        LEFT JOIN order_items oi ON p.id = oi.product_id
        WHERE (:shopId IS NULL OR p.shop_id = :shopId)
        AND (:keyword IS NULL OR p.name LIKE CONCAT('%', :keyword, '%'))
        AND (:status IS NULL OR p.status = :status)
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        GROUP BY p.id
        ORDER BY
            CASE WHEN :sortBy = 'price'      AND :sortDir = 'asc'  THEN p.price END ASC,
            CASE WHEN :sortBy = 'price'      AND :sortDir = 'desc' THEN p.price END DESC,
            CASE WHEN :sortBy = 'totalOrders'                      THEN COUNT(oi.id) END DESC,
            p.created_at DESC
        """,
            countQuery = """
        SELECT COUNT(*) FROM products p
        WHERE (:shopId IS NULL OR p.shop_id = :shopId)
        AND (:keyword IS NULL OR p.name LIKE CONCAT('%', :keyword, '%'))
        AND (:status IS NULL OR p.status = :status)
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        """,
            nativeQuery = true)
    Page<Object[]> searchProducts(
            @Param("shopId")   Integer shopId,
            @Param("keyword")  String keyword,
            @Param("status") ProductStatus status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("sortBy")   String sortBy,
            @Param("sortDir")  String sortDir,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE Products p SET p.stockQuantity = p.stockQuantity - :qty WHERE p.id = :id AND p.stockQuantity >= :qty")
    int decreaseStock(@Param("id") int id, @Param("qty") int qty);

    @Modifying
    @Query("UPDATE Products p SET p.stockQuantity = p.stockQuantity + :qty WHERE p.id = :id")
    int increaseStock(@Param("id") int id, @Param("qty") int qty);
}