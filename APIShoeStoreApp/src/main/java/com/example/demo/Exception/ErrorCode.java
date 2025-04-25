package com.example.demo.Exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error"),
    INVALID_KEY (1001, "Invalid Key"),
    EMAIL_EXISTED(1002, "Email already existed"),
    EMAIL_NOT_FOUND(1003, "Email not found"),
    PASSWORD_INVALID(1005, "Password must be at least 8 characters"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    OTP_NOT_MATCHED(1007, "OTP does not match"),
    OTP_EXPIRED(1008, "OTP expired"),
    PRODUCT_NOT_FOUND(2001, "Product not found"),
    ;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
