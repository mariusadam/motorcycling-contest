package com.ubb.mpp.services.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Marius Adam
 */
@Service
public class EntityValidator implements ValidatorInterface {
    private Validator javaxValidator;

    @Autowired
    public EntityValidator(Validator validator) {
        this.javaxValidator = validator;
    }

    @Override
    public <T> void validate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = javaxValidator.validate(obj);
        StringBuilder sb = new StringBuilder();
        constraintViolations.forEach(
                tConstraintViolation -> sb
                        .append(tConstraintViolation.getMessage())
                        .append(System.lineSeparator())
        );

        if (sb.length() > 0) {
            throw new ValidationException(sb.toString());
        }
    }
}
