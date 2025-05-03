package com.example.demo.Repository;

import com.example.demo.DTO.Response.OrderHistoryResponse;
import com.example.demo.Entity.Order;
import com.example.demo.Enum.OrderStatus;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository {
    List<Order> findByStatus(OrderStatus status);




}
