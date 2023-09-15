package com.github.register.domain.auth.provider;

import com.github.register.domain.auth.AuthenticAccount;
import javax.inject.Named;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;



/**
 * Pre-authenticated Authenticator
 * <p>
 * 1.Pre-validator means that the identity has already been validated elsewhere (by a third party)
 *
 * 2.The purpose of the pre-validator is to integrate a third-party identity management system
 * into a Spring application with Spring security,
 * which in this project is used when the JWT token is refreshed after it expires
 *
 * 3.At this time, just check whether the user has deactivation, lockout, password expiration, account expiration, etc.
 * If not, you can re-issue the access token to the client according to the JWT token refresh expiration period
 *
 * @author zhouzhiming
 * @author sniper
 * @date
 * @see <a href="https://docs.spring.io/spring-security/site/docs/3.0.x/reference/preauth.html">Pre-Authentication Scenarios</a>
 **/
@Named
public class PreAuthenticatedAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
            AuthenticAccount user = (AuthenticAccount) ((UsernamePasswordAuthenticationToken) authentication.getPrincipal()).getPrincipal();
            // Check that the user is not deactivated, locked out, password expired, account expired, etc.
            // In this project, none of these features are enabled, so this check would actually pass,
            // but for the sake of rigor and future expansion, it's done in order.
            if (user.isEnabled() && user.isCredentialsNonExpired() && user.isAccountNonExpired() && user.isCredentialsNonExpired()) {
                return new PreAuthenticatedAuthenticationToken(user, "", user.getAuthorities());
            } else {
                throw new DisabledException("Incorrect user status");
            }
        } else {
            throw new PreAuthenticatedCredentialsNotFoundException("Pre-verification failed, where did the uploaded token come from?");
        }
    }

    /**
     * Determine which authentications this authenticator can handle
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
