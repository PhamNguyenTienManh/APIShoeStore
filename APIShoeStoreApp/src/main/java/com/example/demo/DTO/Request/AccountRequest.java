package com.example.demo.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {
    String email;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String fullName;

    @Size(min = 10, message = "PHONE_NUMBER_INVALID")
    String phone;
}
