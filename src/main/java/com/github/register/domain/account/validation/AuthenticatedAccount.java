package com.github.register.domain.account.validation;


import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The user must be the same as the currently logged-in user
 * The equivalent of using Spring Security's @PreAuthorize("#{user.name == authentication.name}") authentication
 *
 * @author sniper
 * @date
 **/
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Constraint(validatedBy = AccountValidation.AuthenticatedAccountValidator.class)
public @interface AuthenticatedAccount {
    String message() default "Not the currently logged-in user";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
