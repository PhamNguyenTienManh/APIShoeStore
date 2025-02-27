package com.example.demo.Service;

import com.example.demo.DTO.Request.OTPRequest;
import com.example.demo.Entity.OTP;
import com.example.demo.Exception.AppException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.Repository.OTPRepository;
import com.example.demo.Util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPService {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private EmailUtil emailUtil;

    public static String generateOtpCode() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void saveAndSendOTP(String email) {
        String otpCode = generateOtpCode();
        emailUtil.sendOTP(email, otpCode);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plus(5, ChronoUnit.MINUTES);
        Optional<OTP> existingOtp = otpRepository.findByEmail(email);
        OTP otp = new OTP();
        if (existingOtp.isPresent()) {
            otp = existingOtp.get();
            otp.setOtpCode(otpCode);
            otp.setCreatedAt(now);
            otp.setExpirationTime(expireTime);
            otp.setIsUsed(false);
            otpRepository.save(otp);
        }
        else {
            otp.setOtpCode(otpCode);
            otp.setEmail(email);
            otp.setCreatedAt(now);
            otp.setExpirationTime(expireTime);
            otp.setIsUsed(false);
            otpRepository.save(otp);
        }

    }

    public boolean verifyOTP(OTPRequest otpRequest) {
        OTP otp = otpRepository.findOTPByEmailAndIsUsedFalse(otpRequest.getEmail())
                .orElseThrow(()-> new AppException(ErrorCode.OTP_NOT_MATCHED));


        if (!otp.getOtpCode().equals(otpRequest.getOtp())) {
            throw new AppException(ErrorCode.OTP_NOT_MATCHED);
        }

        // Kiểm tra OTP còn hạn không
        if (otp.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        otp.setIsUsed(true);
        otpRepository.save(otp);
        return true;
    }


}
