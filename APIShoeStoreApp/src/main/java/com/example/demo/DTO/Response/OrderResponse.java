package com.example.demo.DTO.Response;

import com.example.demo.Enum.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    private Long id;
    private String name;
    private String phone;
    private String province;
    private String district;
    private String commune;
    private String detailedAddress;
    private LocalDateTime orderDate;
    private Double total;
    private OrderStatus status;
    private List<OrderDetailResponse> orderDetails;
}
