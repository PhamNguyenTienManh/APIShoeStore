package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderDetail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "oid", nullable = false)
    @JsonBackReference
    Order order;

    @ManyToOne
    @JoinColumn(name = "variantId")
    ProductVariant variant;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    Integer quantity;

    @Column(nullable = false)
    BigDecimal total;
}

