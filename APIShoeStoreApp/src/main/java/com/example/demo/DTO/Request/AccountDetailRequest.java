package com.example.demo.DTO.Request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailRequest {
    String fullname;
    String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;

}
