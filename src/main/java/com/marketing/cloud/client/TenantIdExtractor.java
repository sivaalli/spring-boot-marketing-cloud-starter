package com.marketing.cloud.client;

import java.net.URI;

@FunctionalInterface
public interface TenantIdExtractor {

    String extractTenantId(URI url);
}
