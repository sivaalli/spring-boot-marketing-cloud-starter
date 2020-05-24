package com.marketing.cloud.client;

import java.net.URI;

public class McTenantIdExtractor implements TenantIdExtractor {

    private static final String DOT = ".";
    @Override
    public String extractTenantId(URI url) {
        final String host = url.getHost();
        return host.substring(0, host.indexOf(DOT));
    }
}
