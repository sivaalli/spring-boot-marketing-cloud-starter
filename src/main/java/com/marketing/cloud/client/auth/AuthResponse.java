package com.marketing.cloud.client.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private int expiresInSecs;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("rest_instance_url")
    private String restInstanceUrl;
    @JsonProperty("soap_instance_url")
    private String soapInstanceUrl;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresInSecs() {
        return expiresInSecs;
    }

    public void setExpiresInSecs(int expiresInSecs) {
        this.expiresInSecs = expiresInSecs;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRestInstanceUrl() {
        return restInstanceUrl;
    }

    public void setRestInstanceUrl(String restInstanceUrl) {
        this.restInstanceUrl = restInstanceUrl;
    }

    public String getSoapInstanceUrl() {
        return soapInstanceUrl;
    }

    public void setSoapInstanceUrl(String soapInstanceUrl) {
        this.soapInstanceUrl = soapInstanceUrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accessToken", "REDACTED")
                .add("tokenType", tokenType)
                .add("expiresInSecs", expiresInSecs)
                .add("scope", scope)
                .add("restInstanceUrl", restInstanceUrl)
                .add("soapInstanceUrl", soapInstanceUrl)
                .toString();
    }
}