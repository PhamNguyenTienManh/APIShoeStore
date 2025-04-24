package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ProductVariant")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "pId", nullable = false)
    Product product;


    @Column(nullable = false)
    Integer size;

    @Column(nullable = false)
    Integer stock;
}

