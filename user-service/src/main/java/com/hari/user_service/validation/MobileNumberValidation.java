package com.hari.user_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileNumberValidation implements ConstraintValidator<ValidMobileNumber, String> {

    @Override
    public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
        if (mobileNumber == null) return false;
        return mobileNumber.matches("^\\+91\\s?[6-9]\\d{9}$");
    }
}

