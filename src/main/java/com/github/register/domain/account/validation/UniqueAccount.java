package com.github.register.domain.account.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates that an account is unique
 * <p>
 * The unique means that not only the username, but also the cell phone and email are not allowed to be duplicated.
 *
 * @author zhouzhiming
 * @author sniper
 * @date
 **/
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Constraint(validatedBy = AccountValidation.UniqueAccountValidator.class)
public @interface UniqueAccount {
    String message() default "username, e-mail address and cell phone number are not allowed to be duplicated with existing accounts.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
