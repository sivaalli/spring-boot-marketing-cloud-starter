package com.marketing.cloud.client.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.marketing.cloud.client.config.McConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class McAuthenticator implements Authenticator {

    private static final Logger logger = LoggerFactory.getLogger(McAuthenticator.class);
    private static final String TSSD_AUTH_URL = "https://%s.auth.marketingcloudapis.com/v2/token";
    private static final char SPACE = ' ';

    private final RestTemplate rest;
    private final McConfigurationProperties config;
    private final AuthRequest authRequest;

    public McAuthenticator(RestTemplate rest, McConfigurationProperties config) {
        this.rest = rest;
        this.config = config;
        authRequest = baseRequest();
    }

    @Override
    public AuthResponse authenticate(String tenantId) {
        logger.info("Starting authentication with marketing cloud for tenant id [{}].", tenantId);
        final String tenantSpecificAuthUrl = String.format(TSSD_AUTH_URL, tenantId);
        final ResponseEntity<AuthResponse> response;
        try {
            response = rest.postForEntity(tenantSpecificAuthUrl, authRequest, AuthResponse.class);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }

        final AuthResponse responseBody = response.getBody();
        if (responseBody == null) {
            throw new AuthenticationException("Response for authentication request is null.");
        }

        logger.info("Successful authenticating with marketing cloud for tenant id [{}].", tenantId);
        return response.getBody();
    }

    private AuthRequest baseRequest() {
        final AuthRequest authRequest = new AuthRequest();
        authRequest.setClientId(config.getClientId());
        authRequest.setGrantType("client_credentials");
        authRequest.setClientSecret(config.getClientSecret());
        authRequest.setScope(Joiner.on(SPACE).join(config.getScope()));
        return authRequest;
    }

    private static class AuthRequest {

        @JsonProperty("grant_type")
        private String grantType;
        @JsonProperty("client_id")
        private String clientId;
        @JsonProperty("client_secret")
        private String clientSecret;
        @JsonProperty("scope")
        private String scope;
        @JsonProperty("account_id")
        private String accountId;

        public String getGrantType() {
            return grantType;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }
    }
}
