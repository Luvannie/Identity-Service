package com.luvannie.identity_service.exception;

public enum ErrorCode {
    USER_NOT_FOUND(404, "User not found"),
    USERNAME_ALREADY_EXISTS(400, "Username already exists"),
    UNCATEGORIZED(999, "Uncategorized error"),
    INVALID_PASSWORD(401, "Invalid password"),
    INVALID_KEY(401, "Invalid key"),
    USER_NOT_EXIST(404, "User does not exist"),
    UNAUTHENTICATED(401, "Unauthenticated")
    ;
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
