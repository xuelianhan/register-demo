package com.github.register.domain.account.validation;


import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Represents that a user is present in the data warehouse
 *
 * @author sniper
 * @date
 **/
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Constraint(validatedBy = AccountValidation.ExistsAccountValidator.class)
public @interface ExistsAccount {
    String message() default "Account does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
