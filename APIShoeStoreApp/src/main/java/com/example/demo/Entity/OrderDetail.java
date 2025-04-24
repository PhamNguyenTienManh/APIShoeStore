package com.example.demo.Entity;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oid", nullable = false)
    Orders order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "variantId")
    ProductVariant variant;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    Integer quantity;

    @Column(nullable = false)
    BigDecimal total;
}

