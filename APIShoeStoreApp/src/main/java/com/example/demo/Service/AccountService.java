package com.example.demo.Service;
import com.example.demo.DTO.Request.AccountDetailRequest;
import com.example.demo.DTO.Request.AccountImage;
import com.example.demo.DTO.Request.AccountRequest;
import com.example.demo.DTO.Request.LoginRequest;
import com.example.demo.DTO.Response.LoginResponse;
import com.example.demo.DTO.Response.UserDetailResponse;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.Cart;
import com.example.demo.Exception.AppException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.CartRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@Service
public class AccountService {
    AccountRepository accountRepository;
    CartRepository cartRepository;
    PasswordEncoder passwordEncoder;

    public boolean checkExistingEmail(String email) {
        if (accountRepository.findByEmail(email).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean createAccount(AccountRequest accountRequest) {
       if (accountRepository.findByEmail(accountRequest.getEmail()).isPresent()) {
           throw new AppException(ErrorCode.EMAIL_EXISTED);
       }
       Account account = new Account();
       account.setEmail(accountRequest.getEmail());
       account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
       account.setFullname(accountRequest.getFullName());
       account.setPhone(accountRequest.getPhone());

        // Lưu thông tin tài khoản
       accountRepository.save(account);

        // Tạo giỏ hàng cho người dùng
        Cart cart = new Cart();
        cart.setAccount(account);
        cartRepository.save(cart);
        return true;
    }

    public boolean updateAccount(AccountRequest accountRequest) {
        Account account = accountRepository.findByEmail(accountRequest.getEmail())
                        .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        accountRepository.save(account);
        return true;
    }


    public LoginResponse authenticateAccount(LoginRequest loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        LoginResponse loginResponse = new LoginResponse();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), account.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        else{
            loginResponse.setUserId(account.getId());
        }
         return loginResponse;
    }

    public boolean updateAccountDetails(Long id, AccountDetailRequest accountDetailRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        account.setFullname(accountDetailRequest.getName());
        account.setPhone(accountDetailRequest.getPhone());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(accountDetailRequest.getBirthday(), formatter);
        account.setDob(date);

        accountRepository.save(account);
        return true;
    }
    public UserDetailResponse getUserDetail(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setName(account.get().getFullname());
        userDetailResponse.setNumber(account.get().getPhone());
        userDetailResponse.setBirthday(account.get().getDob());
        userDetailResponse.setImage(account.get().getImage());
        return userDetailResponse;
    }
    public Boolean updateImage(Long id, AccountImage accountImage){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        account.setImage(accountImage.getImage());
        accountRepository.save(account);
        return true;
    }
    public String findImageURL(Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        return account.getImage();
    }

}
