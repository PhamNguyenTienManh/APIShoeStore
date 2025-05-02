package com.example.demo.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    private Long variantId;
    private String productName;
    private Double price;
    private int quantity;
    private Double total;

}
