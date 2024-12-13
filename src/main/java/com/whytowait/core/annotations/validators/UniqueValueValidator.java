package com.whytowait.core.annotations.validators;

import com.whytowait.core.annotations.UniqueValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, List<?>> {

    private String fieldName;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Nothing to validate
        }

        Set<Object> seenValues = new HashSet<>();
        for (Object item : value) {
            try {
                Field field = item.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(item);

                if (!seenValues.add(fieldValue)) {
                    return false; // Duplicate value found
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Error accessing field: {}", fieldName, e);
                return false;
            }
        }

        return true;
    }
}
