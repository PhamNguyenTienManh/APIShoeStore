package com.example.demo.Exception;

public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String customMessage;

    // Constructor mặc định (dùng message mặc định từ ErrorCode)
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.customMessage = null; // không có message tùy chỉnh
    }

    // Constructor có message tùy chỉnh
    public AppException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}

