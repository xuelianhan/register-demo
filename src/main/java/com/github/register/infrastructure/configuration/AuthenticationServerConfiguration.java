package com.github.register.infrastructure.configuration;

import com.github.register.domain.auth.provider.PreAuthenticatedAuthenticationProvider;
import com.github.register.domain.auth.provider.UsernamePasswordAuthenticationProvider;
import com.github.register.domain.auth.service.AuthenticAccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * User authentication server configuration of Spring Security
 * <p>
 * Borrowing Spring Security as the authentication server informs the server of the pathway through which to query the user,
 * encrypt the password, and verify the user's authenticity.
 *
 * Instead of using the authentication form provided by Spring Security,
 * we actually chose the front-end password mode via OAuth2 to complete the authentication at the same time as the authorization process.
 *
 * Because the server-side security mechanism
 * (method authorization judgment, OAuth2 password mode user authentication, password encryption algorithms)
 * is still built on top of Spring Security.
 *
 * So our authentication service, user information service still inherits the base class provided by Spring Security,
 * and is registered with Spring Security here.
 *
 * @author zhouzhiming
 * @author sniper
 *
 * @date
 **/
@Configuration
@EnableWebSecurity
public class AuthenticationServerConfiguration extends WebSecurityConfiguration {

    @Autowired
    private AuthenticAccountDetailsService authenticAccountDetailsService;

    @Autowired
    private UsernamePasswordAuthenticationProvider userProvider;

    @Autowired
    private PreAuthenticatedAuthenticationProvider preProvider;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Need to expose the AuthenticationManager to the public,
     * so that it can be used in the authorization server {@link AuthorizationServerConfiguration}
     * to complete the authentication of the username and password.
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configure Spring Security's security authentication service.
     * Web security settings for Spring Security will be done in the Resource Server Configuration
     * {@link ResourceServerConfiguration}
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticAccountDetailsService).passwordEncoder(encoder);
        auth.authenticationProvider(userProvider);
        auth.authenticationProvider(preProvider);
    }
}
