package com.github.register.domain.auth.service;

import com.github.register.domain.auth.AuthenticAccountRepository;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * Inquiry Service for Certified User Information
 * <p>
 * {@link UserDetailsService}
 * @author sniper
 */
@Named
public class AuthenticAccountDetailsService implements UserDetailsService {

    @Inject
    private AuthenticAccountRepository accountRepository;

    /**
     * Query user roles and permissions based on username.
     * If the username can not be queried to the corresponding user,
     * or permissions do not meet,
     * please directly throw {@link UsernameNotFoundException},
     * do not return null
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username);
    }

}
