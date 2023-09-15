package com.github.register.domain.account.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 代表用户必须与当前登陆的用户一致
 * 相当于使用Spring Security的@PreAuthorize("#{user.name == authentication.name}")的验证
 *
 * @author
 * @date
 **/
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Constraint(validatedBy = AccountValidation.AuthenticatedAccountValidator.class)
public @interface AuthenticatedAccount {
    String message() default "不是当前登陆用户";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
