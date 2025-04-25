package com.example.demo.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    String name;
    String image;
    Double price;
    String description;
    Float feedbackStar;
    Set<Integer> size;
}
