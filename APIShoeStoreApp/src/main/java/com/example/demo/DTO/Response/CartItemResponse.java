package com.example.demo.DTO.Response;


import com.example.demo.Entity.Cart;
import com.example.demo.Entity.ProductVariant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Long id;
    String productName;
    Integer size;
    Integer quantity;
    double total_price;
}
