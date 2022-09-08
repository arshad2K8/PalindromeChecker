package com.palindrome.check.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PalindromeInputValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PalindromeInputConstraint {
    String message() default "Invalid palindrome input";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
