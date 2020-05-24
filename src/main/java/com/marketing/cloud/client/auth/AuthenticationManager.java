package com.marketing.cloud.client.auth;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Duration;

/**
 * An {@linkplain Authenticator} that caches token until expiration.
 *
 */
public class AuthenticationManager implements Authenticator {
    private final Cache<String, AuthResponse> tokenCache;
    private final Authenticator authenticator;

    public AuthenticationManager(Authenticator authenticator) {
        this.authenticator = authenticator;
        tokenCache = Caffeine.newBuilder()
                //https://developer.salesforce.com/docs/atlas.en-us.mc-app-development.meta/mc-app-development/access-token-s2s.htm
                .expireAfterWrite(Duration.ofMinutes(18))
                .recordStats()
                .build();
    }

    @Override
    public AuthResponse authenticate(String tenantId) {
        try {
            return tokenCache.get(tenantId, authenticator::authenticate);
        } catch (Exception e) {
            throw new AuthenticationException("Cannot retrieve token from cache", e);
        }
    }
}
