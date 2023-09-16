package com.github.register.infrastructure.configuration;

import com.github.register.domain.auth.service.AuthenticAccountDetailsService;
import com.github.register.domain.auth.service.JWTAccessTokenService;
import com.github.register.domain.auth.service.OAuthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * Spring Security OAuth2 Authorization Server Configuration
 * <p>
 * In this configuration, information about the authorization service Endpoint is set
 * (the location of the endpoint, the request method, what kind of token to use, and what kind of clients are supported)
 * and the user authentication service and user details query service required for OAuth2's password mode.
 *
 * @author sniper
 * @date
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    /**
     * Service of tokenization
     */
    @Autowired
    private JWTAccessTokenService tokenService;

    /**
     * Service of OAuth2 Client Information
     */
    @Autowired
    private OAuthClientDetailsService clientService;

    /**
     * Authentication Service Manager
     * <p>
     * An Authentication Service Manager contains multiple Providers that can perform different types of authentication.
     * Authentication services are defined by the Authentication Server
     * {@link AuthenticationServerConfiguration}
     * and provide the injection source.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Service of User Information
     */
    @Autowired
    private AuthenticAccountDetailsService accountService;


    /**
     * Service of Configuring the Client Details
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }

    /**
     * Configure the service Endpoint for authorization
     * <p>
     * Spring Security OAuth2 automatically generates the following endpoints based on the configured authentication service, user details service, and token service:
     * /oauth/authorize: authorization endpoints
     * /oauth/token: token endpoints
     * /oauth/confirm_access: user confirmation authorization submission endpoints
     * /oauth/error: authorization service error message endpoint
     * /oauth/check_token: token resolution endpoint for resource service access
     * /oauth/token_key: endpoint that provides the public key that the resource service needs to decode during authentication if JWT uses an asymmetric encryption algorithm.
     * If necessary, these endpoints can use the pathMapping() method to modify their location and the prefix() method to set the path prefix.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoint) {
        endpoint.authenticationManager(authenticationManager)
                .userDetailsService(accountService)
                .tokenServices(tokenService)
                // Control the type of access requested by the TokenEndpoint endpoint, default HttpMethod.POST
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * Configure security constraints on the Endpoint itself as published by OAuth2
     * <p>
     * The default access rules for these Endpoints were originally:
     * 1. endpoints have HTTP Basic Authentication turned on, which is turned off via allowFormAuthenticationForClients(),
     * i.e., allowing authentication via forms.
     *
     * 2. endpoints are accessed with denyAll(), which can be changed to permitAll() here with a SpringEL expression
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
    }

}
