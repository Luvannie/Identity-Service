package com.luvannie.identity_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

// chuan hoa du lieu tra ve
@JsonInclude(JsonInclude.Include.NON_NULL) // chi tra ve nhung truong khac null
public class ApiResponse<T> {
    private int code;
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
