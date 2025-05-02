package com.example.demo.Controller;

import com.example.demo.DTO.Request.AccountRequest;
import com.example.demo.DTO.Request.LoginRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.Entity.Account;
import com.example.demo.Service.AccountService;
import jakarta.validation.Valid;
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
@RequestMapping("/shoestore/account")
public class AccountController {

    AccountService accountService;

    // Kiểm tra email đã tồn tại chưa
    @GetMapping("/checkmail/{email}")
    public APIResponse<Boolean> checkExistingEmail(@PathVariable String email) {
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        apiResponse.setResult(accountService.checkExistingEmail(email));
        return apiResponse;
    }

    @PostMapping("/register")
    public  APIResponse<Boolean> createAccount (@RequestBody @Valid AccountRequest accountRequest) {
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        apiResponse.setResult(accountService.createAccount(accountRequest));
        return apiResponse;
    }

    @PostMapping("/forget-password")
    public  APIResponse<Boolean> updateAccount (@RequestBody @Valid AccountRequest accountRequest) {
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        apiResponse.setResult(accountService.updateAccount(accountRequest));
        return apiResponse;
    }

    @PostMapping("/login")
     public APIResponse<Boolean> authenticateAccount (@RequestBody LoginRequest loginRequest) {
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        apiResponse.setResult(accountService.authenticateAccount(loginRequest));
        return apiResponse;
    }


}
