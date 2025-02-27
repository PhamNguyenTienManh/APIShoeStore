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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "otps") // Tên bảng trong cơ sở dữ liệu
public class OTP implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long otpId;

    @Column(name = "email", nullable = false, length = 100)
    String email;

    @Column(name = "otp_code", nullable = false, length = 10)
    String otpCode;

    @Column(name = "expiration_time", nullable = false)
    LocalDateTime expirationTime;

    @Column(name = "is_used", nullable = false) // Trạng thái OTP đã dùng hay chưa
    Boolean isUsed = false;

    @Column(name = "created_at")
    LocalDateTime createdAt;
}