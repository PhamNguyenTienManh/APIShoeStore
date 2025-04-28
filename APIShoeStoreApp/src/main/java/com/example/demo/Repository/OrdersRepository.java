package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import com.example.demo.Enum.OrderStatus;

import java.util.List;

public interface OrdersRepository {
    List<Order> findByStatus(OrderStatus status);

}
