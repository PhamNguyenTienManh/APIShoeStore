package com.example.demo.Entity;

import com.example.demo.Enum.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Payment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "oid")
    Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PaymentType type;

    @Column(nullable = false)
    LocalDate date;

    @Column(nullable = false)
    Double totalPrice;
}

