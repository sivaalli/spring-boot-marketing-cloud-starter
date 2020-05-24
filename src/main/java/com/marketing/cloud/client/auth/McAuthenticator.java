package com.marketing.cloud.client.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marketing.cloud.client.config.McConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class McAuthenticator implements Authenticator {

    private static final Logger logger = LoggerFactory.getLogger(McAuthenticator.class);

    private final RestTemplate rest;
    private final McConfigurationProperties config;
    private final String authUrl;

    public McAuthenticator(RestTemplate rest, McConfigurationProperties config) {
        this.rest = rest;
        this.config = config;
        this.authUrl = config.getAuthUrl();
    }

    @Override
    public AuthResponse authenticate(String tenantId) {
        logger.info("Starting authentication with marketing cloud for tenant id [{}].", tenantId);
        final String tenantSpecificAuthUrl = String.format(authUrl, tenantId);
        final ResponseEntity<AuthResponse> response;
        try {
            response = rest.postForEntity(tenantSpecificAuthUrl, baseRequest(), AuthResponse.class);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }

        final AuthResponse responseBody = response.getBody();
        if (responseBody == null) {
            throw new AuthenticationException("Response for authentication request is null.");
        }

        logger.info("Successful authenticating with marketing cloud for tenant id [{}]. Auth response is {}", tenantId, responseBody);
        return response.getBody();
    }

    private AuthRequest baseRequest() {
        final AuthRequest authRequest = new AuthRequest();
        authRequest.setClientId(config.getClientId());
        authRequest.setClientSecret(config.getClientSecret());
        authRequest.setScope(config.getScope());
        return authRequest;
    }

    private static class AuthRequest {

        @JsonProperty("grant_type")
        private String grantType = "client_credentials";
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
