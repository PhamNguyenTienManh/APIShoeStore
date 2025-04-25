package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @JsonBackReference
    Product product;

    @Column(nullable = false)
    Integer size;

    @Column(nullable = false)
    Integer stock;


}

