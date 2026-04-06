package com.shoppe.specification;

import com.shoppe.constant.ProductStatus;
import com.shoppe.dto.request.ProductSearchRequest;
import com.shoppe.entity.Products;
import com.shoppe.entity.Shops;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    private ProductSpecification() {}

    /**
     * Build toàn bộ điều kiện lọc từ request
     */
    public static Specification<Products> buildSpec(ProductSearchRequest req) {
        return (root, query, cb) -> {

            // Join với bảng shops để lọc theo shopId và rating
            Join<Products, Shops> shopJoin = root.join("shop", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            // ── 1. Mặc định chỉ lấy sản phẩm Active ──────────────────
            if (req.getStatus() != null && !req.getStatus().isBlank()) {
                try {
                    ProductStatus statusEnum = ProductStatus.valueOf(req.getStatus());
                    predicates.add(cb.equal(root.get("status"), statusEnum));
                } catch (IllegalArgumentException ignored) {
                    // Status không hợp lệ → bỏ qua filter này
                }
            } else {
                predicates.add(cb.equal(root.get("status"), ProductStatus.Active));
            }

            // ── 2. Tìm theo keyword (tên + mô tả) ────────────────────
            if (req.getKeyword() != null && !req.getKeyword().isBlank()) {
                String pattern = "%" + req.getKeyword().toLowerCase().trim() + "%";
                Predicate byName = cb.like(cb.lower(root.get("name")), pattern);
                Predicate byDesc = cb.like(cb.lower(root.get("description")), pattern);
                predicates.add(cb.or(byName, byDesc));
            }

            // ── 3. Lọc theo shopId ────────────────────────────────────
            if (req.getShopId() != null) {
                predicates.add(cb.equal(shopJoin.get("id"), req.getShopId()));
            }

            // ── 4. Lọc theo khoảng giá ────────────────────────────────
            if (req.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), req.getMinPrice()));
            }
            if (req.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), req.getMaxPrice()));
            }

            // ── 5. Lọc theo rating shop tối thiểu ────────────────────
            if (req.getMinRating() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        shopJoin.get("ratingAverage"), req.getMinRating()
                ));
            }

            // ── 6. Sắp xếp ───────────────────────────────────────────
            if (query != null) {
                switch (req.getSortBy()) {
                    case "price_asc"   -> query.orderBy(cb.asc(root.get("price")));
                    case "price_desc"  -> query.orderBy(cb.desc(root.get("price")));
                    case "rating_desc" -> query.orderBy(cb.desc(shopJoin.get("ratingAverage")));
                    default            -> query.orderBy(cb.desc(root.get("createdAt"))); // newest
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}