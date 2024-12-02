package com.whytowait.core.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueValueValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {
    String message() default "Duplicate values are not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName(); // Field to check for uniqueness
}
