package com.marketing.cloud.client.auth;

@FunctionalInterface
public interface Authenticator {
    AuthResponse authenticate(String tenantId);
}
