package com.github.register.domain.auth.service;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


import java.util.HashMap;
import java.util.Map;

/**
 * JWT access token
 * <p>
 * The structure of a JWT token is composed of three parts: [Token Header] . [Payload]. [Signature].
 *
 * Token header: defines the metadata of the token, such as the signature algorithm used by the token,
 * the default is HMAC SHA256 algorithm.
 *
 * Load information: data customized by the issuer, generally including the expiration time (Expire),
 * the scope of the authorization (Authority), the token ID number (JTI) and so on.
 *
 * Signature: Signature is a digital signature using the private key and the algorithm specified in the header,
 * the first two parts to prevent data tampering.
 *
 * Above, the token header and load are JSON structures, signed after Base64URLEncode,
 * and then connected by "." connection, constituting the token message
 * <p>
 * Spring Security OAuth2's {@link JwtAccessTokenConverter} provides an implementation of the token's base structure
 * (token header, some loads, such as expiration time, JTI) converter.
 *
 * Inherit this class and use it by adding your own defined load information.
 * Generally speaking, the load should at least tell the server who the current user is,
 * but should not store too much information to cause the HTTP Header is too large,
 * especially should not store sensitive information.
 *
 * @author sniper
 * @date
 */
@Named
public class JWTAccessToken extends JwtAccessTokenConverter {

    // Signed private key
    // The content here is randomly written UUID, according to the JWT protocol default is 256Bit.
    // In fact, any format can be, just pay attention to confidentiality, do not publicize it!
    private static final String JWT_TOKEN_SIGNING_PRIVATE_KEY = "601304E0-8AD4-40B0-BD51-0B432DC47461";

    @Inject
    JWTAccessToken(UserDetailsService userDetailsService) {
        // Set the signature private key
        setSigningKey(JWT_TOKEN_SIGNING_PRIVATE_KEY);
        // Set up a query service that converts the JWT token brought up from the resource request back into user information in the security context.
        // If this service is not set, then the Principal obtained from the JWT token will only have a username
        // (and the token will indeed only have the username stored in it).
        // Provide the user lookup service to the default token converter,
        // so that when the token is converted, the full user object is automatically reduced based on the username.
        // This facilitates later coding (you can get the logged-in user information directly),
        // but it also steadily adds a query (from database/cache) per request, so take it or leave it.
        DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(userDetailsService);
        ((DefaultAccessTokenConverter) getAccessTokenConverter()).setUserTokenConverter(converter);
    }

    /**
     * Enhanced tokens
     * Enhancement is essentially adding extra information to the token's load
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Authentication user = authentication.getUserAuthentication();
        String[] authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
        Map<String, Object> payLoad = new HashMap<>();
        // The default implementation of Spring Security OAuth's JWT token adds a "user_name" entry to store the current username.
        // At Here, mainly for demonstration purposes of the Payload and to make it easier for the client to get it
        // (otherwise, the client would have to decode Base64 from the token to get it),
        // a "username" is set, and the contents of both are the same.
        payLoad.put("username", user.getName());
        payLoad.put("authorities", authorities);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(payLoad);
        return super.enhance(accessToken, authentication);
    }
}
