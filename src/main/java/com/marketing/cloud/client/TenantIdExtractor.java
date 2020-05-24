package com.marketing.cloud.client;

import java.net.URI;
import java.net.URL;

@FunctionalInterface
public interface TenantIdExtractor {

    String extractTenantId(URI url);
}
