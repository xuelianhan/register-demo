package com.github.register.domain.account.validation;

import com.github.register.domain.account.Account;
import com.github.register.domain.account.AccountRepository;
import com.github.register.domain.auth.AuthenticAccount;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Account-object validator
 * <p>
 * For example, when adding a new user, determine if the user object is allowed to be unique,
 * and when modifying a user, determine if the user exists
 *
 * @author sniper
 * @date
 **/
public class AccountValidation<T extends Annotation> implements ConstraintValidator<T, Account> {

    @Inject
    protected AccountRepository repository;

    protected Predicate<Account> predicate = c -> true;

    @Override
    public boolean isValid(Account value, ConstraintValidatorContext context) {
        // In JPA persistence, which is implemented in Hibernate by default, the BeanValidationEventListener is called for validation on insertion and update.
        // The validation behavior should be done in the outer layer if possible. The @Vaild annotation has already triggered validation once in the Resource, which can lead to duplicate execution.
        // The normal way to avoid this is to use grouped validation, but @Vaild doesn't support grouping, @Validated does, but it's a private Spring tag.
        // Another way is to set the javax.persistence.validation.mode parameter in the Hibernate configuration file to "none", which is not bridged by Spring's yml.
        // In order to avoid repetitive validation involving database operations, this null value is added here to make use of Hibernate validation when the validator is not created by Spring.
        return repository == null || predicate.test(value);
    }

    public static class ExistsAccountValidator extends AccountValidation<ExistsAccount> {
        public void initialize(ExistsAccount constraintAnnotation) {
            predicate = c -> repository.existsById(c.getId());
        }
    }

    public static class AuthenticatedAccountValidator extends AccountValidation<AuthenticatedAccount> {
        public void initialize(AuthenticatedAccount constraintAnnotation) {
            predicate = c -> {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if ("anonymousUser".equals(principal)) {
                    return false;
                } else {
                    AuthenticAccount loginUser = (AuthenticAccount) principal;
                    return c.getId().equals(loginUser.getId());
                }
            };
        }
    }

    public static class UniqueAccountValidator extends AccountValidation<UniqueAccount> {
        public void initialize(UniqueAccount constraintAnnotation) {
            predicate = c -> !repository.existsByUsernameOrEmailOrTelephone(c.getUsername(), c.getEmail(), c.getTelephone());
        }
    }

    public static class NotConflictAccountValidator extends AccountValidation<NotConflictAccount> {
        public void initialize(NotConflictAccount constraintAnnotation) {
            predicate = c -> {
                Collection<Account> collection = repository.findByUsernameOrEmailOrTelephone(c.getUsername(), c.getEmail(), c.getTelephone());
                // Changing a username, email,
                // or phone number to something that doesn't duplicate the existing one at all,
                // or only duplicates itself,
                // is not a conflict
                return collection.isEmpty() || (collection.size() == 1 && collection.iterator().next().getId().equals(c.getId()));
            };
        }
    }

}
