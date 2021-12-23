package com.nhlstenden.student.vigmo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class BcryptValidatorImpl implements ConstraintValidator<BcryptValidator, String> {
    @Override
    public void initialize(BcryptValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        //^[$]2[abxy][$]: Algorithm identifier
        //(?:0[4-9]|[12][0-9]|3[01]): Cost, a 2 digit number between 04 and 31
        //[$][./0-9a-zA-Z]{53}$: Salt + Hash, 22 characters for the salt and 31 for the hash
        return value.matches("^[$]2[abxy][$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$");
    }
}
