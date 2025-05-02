package com.example.demo.DTO.Request;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Long accountId;
    String name;
    String phone;
    String province;
    String district;
    String commune;
    String detailedAddress;
    String paymentMethod;
}
