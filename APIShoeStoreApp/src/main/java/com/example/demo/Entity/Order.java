package com.example.demo.Entity;

import com.example.demo.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    Account account;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String phone;

    String province;
    String district;
    String commune;

    @Column(name = "detailedAddress")
    String detailedAddress;

    @Column(name = "orderDate")
    LocalDateTime orderDate;

    @Column(nullable = false)
    Double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderDetail> orderDetails = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;

    Integer isReview;
}

