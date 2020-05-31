package com.marketing.cloud.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.marketing.cloud")
public class McConfigurationProperties {

    private String clientId;
    private String clientSecret;
    private String defaultTenantId;
    private String soapBaseUrl = "https://%s.soap.marketingcloudapis.com/";
    private String restBaseUrl = "https://%s.rest.marketingcloudapis.com/";
    private String authBaseUrl = "https://%s.auth.marketingcloudapis.com/";
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

    public String getSoapBaseUrl() {
        return soapBaseUrl;
    }

    public void setSoapBaseUrl(String soapBaseUrl) {
        this.soapBaseUrl = soapBaseUrl;
    }

    public String getRestBaseUrl() {
        return restBaseUrl;
    }

    public void setRestBaseUrl(String restBaseUrl) {
        this.restBaseUrl = restBaseUrl;
    }

    public String getAuthBaseUrl() {
        return authBaseUrl;
    }

    public void setAuthBaseUrl(String authBaseUrl) {
        this.authBaseUrl = authBaseUrl;
    }
}
