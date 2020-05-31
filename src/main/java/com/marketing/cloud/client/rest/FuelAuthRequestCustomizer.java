package com.marketing.cloud.client.rest;

import com.marketing.cloud.client.TenantIdExtractor;
import com.marketing.cloud.client.auth.Authenticator;
import org.springframework.boot.web.client.RestTemplateRequestCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;

public class FuelAuthRequestCustomizer implements RestTemplateRequestCustomizer<ClientHttpRequest> {
    private static final String BEARER = "Bearer %s";

    private final Authenticator authenticator;
    private final TenantIdExtractor tenantIdExtractor;

    public FuelAuthRequestCustomizer(Authenticator authenticator, TenantIdExtractor tenantIdExtractor) {
        this.authenticator = authenticator;
        this.tenantIdExtractor = tenantIdExtractor;
    }

    @Override
    public void customize(ClientHttpRequest request) {
        final String tenantId = tenantIdExtractor.extractTenantId(request.getURI());
        final String token = authenticator.authenticate(tenantId).getAccessToken();

        request.getHeaders().add(HttpHeaders.AUTHORIZATION, String.format(BEARER, token));
    }
}
