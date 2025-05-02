package com.example.demo.Repository;

import com.example.demo.Entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findProductVariantById(Long id);
    Optional<ProductVariant> findByProductIdAndSize(Long productId, Integer size);
}
