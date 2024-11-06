package com.luvannie.identity_service.validator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({ElementType.FIELD}) // chi dung cho field
@Retention(RUNTIME) // giu lai trong runtime
@Constraint(validatedBy = {DobValidator.class}) // kiem tra qua DobValidator
public @interface DobConstraint {
    String message() default "Invalid date of birth"; // message mac dinh

    int min(); // khai bao gia tri toi thieu

    Class<?>[] groups() default {}; // nhom mac dinh

    Class<?>[] payload() default {}; // payload mac dinh
}
