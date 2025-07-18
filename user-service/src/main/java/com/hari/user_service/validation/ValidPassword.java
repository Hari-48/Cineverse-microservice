package com.hari.user_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented

@Constraint(validatedBy = PasswordValidator.class)

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidPassword {
    String message() default "Invalid password. Must contain at least 8 characters, an uppercase letter, a digit, and a symbol.,eg Raina@03";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
