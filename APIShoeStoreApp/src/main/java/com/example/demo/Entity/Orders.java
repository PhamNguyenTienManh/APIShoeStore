package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
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
    BigDecimal total;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statusId", insertable = false, updatable = false)
    Status status;

}

