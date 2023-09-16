package com.github.register.domain.auth.service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;



/**
 * OAuth2 Client Type Definitions
 * <p>
 * OAuth2 supports four authorization modes, only one of which is defined here as Resource Owner Password Credentials Grant.
 * OAuth2 as an open (for different service providers) authorization protocol,
 * requiring users to provide plaintext usernames, passwords, this "password mode" is not commonly used.
 *
 * And this "password mode" can be used here because the front-end and back-end services belong to the same service provider,
 * in essence, there is no password will not be saved by a third party of sensitive issues.
 *
 * If you only ever consider a monolithic architecture, a single service provider,
 * there is no need to introduce OAuth,
 * Spring Security's form authentication can be a good and convenient solution
 * to the authentication and authorization problem
 * At Here, the password pattern is used to solve the problem.
 *
 * The use of password mode to solve here is for the next stage of demonstration of microservice
 * after the authentication between services in preparation for the subsequent expansion as well as comparison.
 *
 * @author sniper
 * @date
 **/
@Named
public class OAuthClientDetailsService implements ClientDetailsService {

    /**
     * Client ID
     * The client here is the front-end code for this project
     */
    private static final String CLIENT_ID = "imagebase_frontend";
    /**
     * Client key
     * In the OAuth2 protocol, the ID is publicly available,
     * and the key, which proves that the client currently requesting authorization has not been impersonated,
     * should be kept secret
     */
    private static final String CLIENT_SECRET = "imagebase_secret";

    @Inject
    private PasswordEncoder passwordEncoder;

    private ClientDetailsService clientDetailsService;

    /**
     * Construct a password authorization schema
     * <p>
     * Since there is essentially only one client,
     * forget about storage and client additions, deletions, and changes, and configure the client directly in memory
     * <p>
     * Authorization Endpoint example:
     * /oauth/token?grant_type=password & username=#USER# & password=#PWD# & client_id=imagebase_frontend & client_secret=imagebase_secret
     * Refresh Token Endpoint Example:
     * /oauth/token?grant_type=refresh_token & refresh_token=#REFRESH_TOKEN# & client_id=imagebase_frontend & client_secret=imagebase_secret
     */
    @PostConstruct
    public void init() throws Exception {
        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        // Provide the client ID and key,
        // and specify that the client supports both password-authorized and refresh token access types.
        builder.withClient(CLIENT_ID)
                .secret(passwordEncoder.encode(CLIENT_SECRET))
                .scopes("BROWSER")
                .authorizedGrantTypes("password", "refresh_token");
        clientDetailsService = builder.build();
    }

    /**
     * External query authentication method based on client ID
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientDetailsService.loadClientByClientId(clientId);
    }
}
