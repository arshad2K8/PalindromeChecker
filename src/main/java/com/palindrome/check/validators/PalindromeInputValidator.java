package com.palindrome.check.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PalindromeInputValidator implements ConstraintValidator<PalindromeInputConstraint, String> {
    @Override
    public boolean isValid(String inputField, ConstraintValidatorContext constraintValidatorContext) {
        return inputField != null && inputField.matches("[\\S]*$");
    }
}
