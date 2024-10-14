package com.luvannie.identity_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
    private int min;

    @Override
    // khoi tao gia tri toi thieu
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation); //To change body of generated methods, choose Tools | Templates.
        this.min = constraintAnnotation.min();
    }

    @Override
    // kiem tra gia tri hop le
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true;

        long years = ChronoUnit.YEARS.between(value, LocalDate.now());

        return years >= min;
    }
}
