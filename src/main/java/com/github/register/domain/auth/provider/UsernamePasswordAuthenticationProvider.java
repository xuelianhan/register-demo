package com.github.register.domain.auth.provider;

import com.github.register.domain.auth.service.AuthenticAccountDetailsService;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Username, password based authenticator
 * This authenticator will be called by {@link AuthenticationManager}.
 * Authentication Manager supports multiple authentication methods,
 * At here, The username and password based authentication is one of the methods
 *
 * @author zhouzhiming
 * @author sniper
 * @date
 */
@Named
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private AuthenticAccountDetailsService authenticAccountDetailsService;

    @Inject
    private PasswordEncoder passwordEncoder;

    /**
     * Authentication processing
     * <p>
     * Query the user's profile based on the username and compare the encrypted password in the profile.
     * The result will return an Authentication implementation class
     * (in this case, UsernamePasswordAuthenticationToken) means that authentication is successful.
     * If the result returns null or throws a subclass of AuthenticationException, the authentication failed.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().toLowerCase();
        String password = (String) authentication.getCredentials();
        // The subclasses of AuthenticationException define various types of authentication failures.
        // At here, only "user does not exist" and "password is incorrect" are handled.
        // If the user does not exist, an exception is thrown by loadUserByUsername().
        UserDetails user = authenticAccountDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) throw new BadCredentialsException("Incorrect password");
        // Authentication passed, return token
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

    }

    /**
     * Determine which authentications this authenticator can handle
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UsernamePasswordAuthenticationToken.class);
    }

}
