package com.whytowait.core.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null checks if needed
        }

        // Check if the value is a valid enum constant
        return Stream.of(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equals(value.toString()));
    }
}
