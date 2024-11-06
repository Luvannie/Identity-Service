package com.luvannie.identity_service.exception;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.luvannie.identity_service.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handleValidationException(@NotNull MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            // lay thong tin cua exception
            var constraintViolation =
                    exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);
            // lay thong tin cua constraint
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            // lay thong tin chi tiet cua tung param truyen vao annotation
            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());
        // tra ve code va message cua exception
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        // thay the gia tri cua min trong message
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
