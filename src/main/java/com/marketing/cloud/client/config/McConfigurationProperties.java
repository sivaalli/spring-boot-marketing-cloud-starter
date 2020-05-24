package com.marketing.cloud.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.marketing.cloud")
public class McConfigurationProperties {

    private String clientId;
    private String clientSecret;
    private String defaultTenantId;
    private String soapServiceUrl = "https://%s.soap.marketingcloudapis.com/Service.asmx";
    private String restBaseUrl = "https://%s.rest.marketingcloudapis.com/";
    private String authUrl = "https://%s.auth.marketingcloudapis.com/v2/token";
    private String scope;

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

    public String getDefaultTenantId() {
        return defaultTenantId;
    }

    public void setDefaultTenantId(String defaultTenantId) {
        this.defaultTenantId = defaultTenantId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSoapServiceUrl() {
        return soapServiceUrl;
    }

    public void setSoapServiceUrl(String soapServiceUrl) {
        this.soapServiceUrl = soapServiceUrl;
    }

    public String getRestBaseUrl() {
        return restBaseUrl;
    }

    public void setRestBaseUrl(String restBaseUrl) {
        this.restBaseUrl = restBaseUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }
}
