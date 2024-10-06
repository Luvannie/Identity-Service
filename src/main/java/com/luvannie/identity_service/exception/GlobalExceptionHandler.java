package com.luvannie.identity_service.exception;

import com.luvannie.identity_service.dto.response.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// noi bat va xu ly cac exception
public class GlobalExceptionHandler {
//    @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<ApiResponse<Void>> handleRuntimeException(@NotNull RuntimeException e) {
//        ApiResponse<Void> response = new ApiResponse<>();
//        response.setCode(400);
//        response.setMessage(e.getMessage());
//        return ResponseEntity.badRequest().body(response);
//    }
//    //sau khi co runtime exception thi se tra ve bad request va body la message cua exception

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handleValidationException(@NotNull MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> handleAppException(@NotNull AppException e) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(e.getErrorCode().getCode());
        response.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Void>> handleException(@NotNull Exception e) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(ErrorCode.UNCATEGORIZED.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
