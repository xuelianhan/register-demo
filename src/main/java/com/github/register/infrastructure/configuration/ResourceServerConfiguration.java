package com.github.register.infrastructure.configuration;

import com.github.register.domain.auth.service.JWTAccessTokenService;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


/**
 * Resource server configuration
 * <p>
 * There are two main ways to configure resource service access rights:
 * One is configured centrally here via the <code>antMatchers</code> method of {@link HttpSecurity}.
 * The second is to enable global method level security support {@link EnableGlobalMethodSecurity}
 * Configure each resource one by one by using annotations in front of the access methods of each resource,
 * the annotations used include the following:
 * JSR 250 standard annotation {@link RolesAllowed}, complete replacement for Spring's {@link Secured} functionality
 * and Spring annotations {@link PreAuthorize}, {@link PostAuthorize} that can use EL expressions.
 *
 * @author sniper
 * @date
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private JWTAccessTokenService tokenService;

    /**
     * Configure security options related to HTTP access
     */
    public void configure(HttpSecurity http) throws Exception {
        // Bind user state based on JWT, so the server side can be stateless
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // Turn off CSRF (Cross Site Request Forgery) Cross Site Request Forgery Defense
        // To enable this feature only the state is required to store the CSRF Token.
        http.csrf(csrf -> csrf.disable());
        // Turn off the X-Frame-Options option in the HTTP Header to allow pages to open in frame tags
        http.headers(headers -> headers.frameOptions(fo -> fo.disable()));
        // Setting up security rules for services
        http.authorizeRequests().antMatchers("/oauth/**").permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(tokenService);
    }
}
