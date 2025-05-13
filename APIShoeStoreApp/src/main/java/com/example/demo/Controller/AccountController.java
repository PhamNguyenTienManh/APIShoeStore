package com.example.demo.Controller;

import com.example.demo.DTO.Request.AccountDetailRequest;
import com.example.demo.DTO.Request.AccountRequest;
import com.example.demo.DTO.Request.LoginRequest;
import com.example.demo.DTO.Response.APIResponse;
import com.example.demo.DTO.Response.LoginResponse;
import com.example.demo.DTO.Response.UserDetailResponse;
import com.example.demo.Entity.Account;
import com.example.demo.Exception.AppException;
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
     public APIResponse<LoginResponse> authenticateAccount (@RequestBody LoginRequest loginRequest) {
        APIResponse<LoginResponse> apiResponse = new APIResponse<>();
        apiResponse.setResult(accountService.authenticateAccount(loginRequest));
        return apiResponse;
    }

    @PutMapping("/update_accountDetail/{id}")
    @ResponseBody
    public APIResponse<Boolean> updateAccountDetail(@PathVariable Long id, @RequestBody AccountDetailRequest accountDetailRequest) {
        APIResponse<Boolean> apiResponse = new APIResponse<>();
        System.out.println("API update_accountDetail was called with id: " + id);
        System.out.println("Received data: " + accountDetailRequest);
        if (accountDetailRequest == null) {
            apiResponse.setCode(400);
            System.out.println("Lỗi 400");
            apiResponse.setMessage("Account details cannot be null");
            apiResponse.setResult(false);
            return apiResponse;
        }

        try {
            boolean result = accountService.updateAccountDetails(id, accountDetailRequest);
            System.out.println("Received data: " + accountDetailRequest);
            apiResponse.setResult(result);
            return apiResponse;
        } catch (AppException ex) {
            System.out.println("Lỗi 404");
            apiResponse.setCode(404);
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setResult(false);
            return apiResponse;
        }  catch (Exception e) {
        apiResponse.setCode(500);
        apiResponse.setMessage("An error occurred while updating account details");
        apiResponse.setResult(false);
        e.printStackTrace();  // In chi tiết stack trace ra log để xác định nguyên nhân
        return apiResponse;
    }

}
    @GetMapping("/userDetail/{id}")
    @ResponseBody
    public UserDetailResponse getUserDetailByUserId(@PathVariable Long id){
        System.out.println("Received data: " + id);
        return accountService.getUserDetail(id);
    }


}
