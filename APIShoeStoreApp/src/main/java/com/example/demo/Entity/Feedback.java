package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference
    Account account;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    @JsonBackReference
    Product product;


    @Column(columnDefinition = "TEXT")
    String comment;

    @Column
    Integer rate;

    @Column(name = "createdAt")
    LocalDateTime createdAt;
}

