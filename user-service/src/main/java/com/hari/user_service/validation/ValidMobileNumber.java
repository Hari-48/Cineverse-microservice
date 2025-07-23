package com.hari.user_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD })
@Constraint(validatedBy = MobileNumberValidation.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMobileNumber {

    String message() default "Invalid MobileNumber.it should be 10 digits followed by +91 , eg:+91 8220XXXXXX1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};



}


