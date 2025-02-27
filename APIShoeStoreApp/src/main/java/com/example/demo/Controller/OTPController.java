package com.example.demo.Controller;
import com.example.demo.DTO.Request.OTPRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.Service.OTPService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@RequestMapping("shoestore/otp")
public class OTPController {
    OTPService otpService;

    // Tạo OTP và lưu vào cơ sở dữ liệu
    @PostMapping("/generate/{email}")
    public APIResponse<String> generateOTP(@PathVariable String email) {
        otpService.saveAndSendOTP(email);
        APIResponse<String> apiResponse = new APIResponse<>();
        apiResponse.setResult("OTP has been generated");
        return apiResponse;
    }
    // Xác thực OTP
    @PostMapping("/verifyOTP")
    public APIResponse<Boolean> verifyOTP(@RequestBody OTPRequest otpRequest) {
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        apiResponse.setResult(otpService.verifyOTP(otpRequest));
        return apiResponse;
    }

}
