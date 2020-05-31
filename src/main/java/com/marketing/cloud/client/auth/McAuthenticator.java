package com.marketing.cloud.client.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketing.cloud.client.config.McConfigurationProperties;
import com.marketing.cloud.client.rest.McPaths;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class McAuthenticator implements Authenticator {

    private static final Logger logger = LoggerFactory.getLogger(McAuthenticator.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private final McConfigurationProperties config;
    private final CloseableHttpClient httpClient = HttpClients.createMinimal();

    public McAuthenticator(McConfigurationProperties config) throws URISyntaxException {
        this.config = config;
    }

    @Override
    public AuthResponse authenticate(String tenantId) {
        logger.info("Starting authentication with marketing cloud for tenant id [{}].", tenantId);

        final URI tenantSpecificAuthUri = McPaths.getUri(config.getAuthBaseUrl(), tenantId, McPaths.V2_TOKEN);

        HttpPost authPost = new HttpPost(tenantSpecificAuthUri);
        authPost.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        authPost.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        CloseableHttpResponse httpResponse;
        try {
            authPost.setEntity(new ByteArrayEntity(mapper.writeValueAsBytes(baseRequest())));
            httpResponse = httpClient.execute(authPost);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }

        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            throw new AuthenticationException("Entity/Response body obtained is null");
        }

        AuthResponse authResponse;
        try {
            final byte[] bytes = EntityUtils.toByteArray(entity); // also closes underlying stream
            authResponse = mapper.readValue(bytes, AuthResponse.class);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                logger.error("Cannot close Http response after authentication", e);
            }
        }
        logger.info("Successful authenticating with marketing cloud for tenant id [{}]. Auth response is {}", tenantId, authResponse);
        return authResponse;
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
