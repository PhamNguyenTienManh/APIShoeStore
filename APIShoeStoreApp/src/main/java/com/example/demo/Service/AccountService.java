package com.example.demo.Service;
import com.example.demo.DTO.Request.AccountRequest;
import com.example.demo.Entity.Account;
import com.example.demo.Exception.AppException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.Repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
@Service
public class AccountService {
    AccountRepository accountRepository;
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
       accountRepository.save(account);
       return true;
    }

    public boolean updateAccount(AccountRequest accountRequest) {
        Account account = accountRepository.findByEmail(accountRequest.getEmail())
                        .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        accountRepository.save(account);
        return true;
    }


    public boolean authenticateAccount(AccountRequest accountRequest) {
        Account account = accountRepository.findByEmail(accountRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(accountRequest.getPassword(), account.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return authenticated;
    }
}
