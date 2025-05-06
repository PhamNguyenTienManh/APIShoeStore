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
    PHONE_NUMBER_INVALID(1009, "Phone number is invalid"),
    PRODUCT_NOT_FOUND(2001, "Product not found"),
    ACCOUNT_NOT_FOUND(2002, "Account not found"),
    PRODUCT_VARIANT_NOT_FOUND(2003, "Product variant not found"),
    CART_NOT_FOUND(2004, "Cart not found"),
    CART_ITEM_NOT_FOUND(2005, "Cart item not found"),
    CART_IS_EMPTY(2006, "Cart is empty"),
    NOT_ENOUGH_STOCK(2007, "Not enough product in stock"),
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
