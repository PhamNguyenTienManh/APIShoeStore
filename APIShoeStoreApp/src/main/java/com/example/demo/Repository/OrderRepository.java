package com.example.demo.Repository;

import com.example.demo.DTO.Response.OrderHistoryResponse;
import com.example.demo.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
    SELECT new com.example.demo.DTO.Response.OrderHistoryResponse(
        o.id, o.orderDate, o.total, p.name, p.image, d.quantity,v.size, o.commune, o.detailedAddress, o.district, o.province, o.isReview
    )
    FROM Order o
    JOIN o.orderDetails d
    JOIN d.variant v
    JOIN v.product p
    WHERE o.account.id = :userId
    """)
    List<OrderHistoryResponse> getOrderSummariesByUserId(@Param("userId") Long userId);
    Optional<Order> findById (Long orderId);
}
