package com.github.register.domain.account.validation;


import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates that an account's information is conflict-free
 * <p>
 * "Conflict-free" means that the account's sensitive information does not overlap with that of other accounts,
 * e.g.,
 * changing a registered account's email address to a value that
 * matches that of an existing registered account is a conflict.
 *
 * @author sniper
 * @date
 **/
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Constraint(validatedBy = AccountValidation.NotConflictAccountValidator.class)
public @interface NotConflictAccount {
    String message() default "Duplication of accounts' names, e-mail addresses, and cell phone numbers with existing accounts";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
