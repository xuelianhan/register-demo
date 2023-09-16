package com.github.register.domain.auth.service;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * JWT Access Token Service
 * <p>
 *
 * Definitions of how the token is stored, what information it carries, how it is signed, how long it lasts,
 * and other relevant elements are provided in this service.
 *
 * The token service should be called by the authorization server
 * {@link com.github.register.infrastructure.configuration.AuthorizationServerConfiguration}
 * when registering an authentication Endpoint.
 *
 * @author sniper
 * @date
 **/
@Named
public class JWTAccessTokenService extends DefaultTokenServices {

    /**
     * Building JWT tokens with default configuration
     */
    @Inject
    public JWTAccessTokenService(JWTAccessToken token, OAuthClientDetailsService clientService, AuthenticationManager authenticationManager) {
        // Setting up the token's persistence container
        // Token persistence can be done in a variety of ways,
        // a single node service can store it in Session, and a cluster can store it in Redis.
        // JWT is a back-end stateless, front-end storage solution,
        // where the storage of tokens is done by the front-end.
        setTokenStore(new JwtTokenStore(token));
        // Details of clients supported by tokens
        setClientDetailsService(clientService);
        // Set up the AuthenticationManager, which is needed for authentication.
        setAuthenticationManager(authenticationManager);
        // Define additional loads for tokens
        setTokenEnhancer(token);
        // access_token expiration date, in seconds, default 12 hours
        setAccessTokenValiditySeconds(60 * 60 * 3);
        // The duration of the refresh_token, in seconds, 30 days by default.
        // This determines the maximum amount of time the client can choose to "remember the currently logged-in user",
        // i.e., the client doesn't have to ask for the user's authorization again until it expires.
        setRefreshTokenValiditySeconds(60 * 60 * 24 * 15);
        // Whether to support refresh_token, default false
        setSupportRefreshToken(true);
        // Whether to reuse refresh_token, default is true
        // If false, the old refresh_token is deleted and a new one is created for each request refresh.
        setReuseRefreshToken(true);
    }
}
