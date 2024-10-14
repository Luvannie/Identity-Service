package com.luvannie.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    USERNAME_ALREADY_EXISTS(400, "Username already exists", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PASSWORD(401, "Invalid password", HttpStatus.BAD_REQUEST),
    INVALID_KEY(401, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(404, "User does not exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "Unauthorized", HttpStatus.FORBIDDEN),
    INVALID_DATE_OF_BIRTH(400, "Invalid date of birth", HttpStatus.BAD_REQUEST),
    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
