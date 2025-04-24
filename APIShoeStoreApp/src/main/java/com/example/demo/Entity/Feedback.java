package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Feedback")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    Account account;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    Product product;


    @Column(columnDefinition = "TEXT")
    String comment;

    @Column
    Integer rate;

    @Column(name = "createdAt")
    LocalDateTime createdAt;
}

