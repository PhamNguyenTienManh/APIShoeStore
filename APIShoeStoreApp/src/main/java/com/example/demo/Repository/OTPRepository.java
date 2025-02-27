package com.example.demo.Repository;
import com.example.demo.Entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findOTPByEmailAndIsUsedFalse (String email);
    Optional<OTP> findByEmail(String email);
}