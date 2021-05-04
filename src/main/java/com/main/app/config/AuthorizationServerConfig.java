package com.main.app.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;


/**
 * OAuth 2 authorization server configuration.
 * Authorization server is the one responsible for verifying credentials and providing the tokens.
 *
 * @author Nikola
 */
@Configuration
@EnableAuthorizationServer
@NoArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;

    private TokenStore tokenStore;

    private PasswordEncoder passwordEncoder;

    static final String CLIENT_ID = "client_id";
    static final String CLIENT_SECRET = "client_secret";
    static final String GRANT_TYPE_PASSWORD = "password";
    static final String AUTHORIZATION_CODE = "authorization_code";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String IMPLICIT = "implicit";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 60;
    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;

    @Autowired
    public AuthorizationServerConfig(
            AuthenticationManager authenticationManager,
            TokenStore tokenStore,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer
                .inMemory()
                .withClient(CLIENT_ID)
                .secret(passwordEncoder.encode(CLIENT_SECRET))
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients();
    }

}