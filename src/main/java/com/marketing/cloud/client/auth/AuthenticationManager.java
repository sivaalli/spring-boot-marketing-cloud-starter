package com.marketing.cloud.client.auth;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An {@linkplain Authenticator} that caches token until expiration.
 */
public class AuthenticationManager implements Authenticator {
    private final Cache<String, AuthResponse> tokenCache;
    private final Authenticator authenticator;

    public AuthenticationManager(Authenticator authenticator) {
        this.authenticator = authenticator;
        tokenCache = Caffeine.newBuilder()
                .expireAfter(new AuthResponseEntry())
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

    private static class AuthResponseEntry implements Expiry<String, AuthResponse> {
        @Override
        public long expireAfterCreate(@NonNull String key, @NonNull AuthResponse value, long currentTime) {
            return value.getExpiresInSecs();
        }

        @Override
        public long expireAfterUpdate(@NonNull String key, @NonNull AuthResponse value, long currentTime, @NonNegative long currentDuration) {
            return value.getExpiresInSecs();
        }

        @Override
        public long expireAfterRead(@NonNull String key, @NonNull AuthResponse value, long currentTime, @NonNegative long currentDuration) {
            return Long.MAX_VALUE;
        }
    }
}
