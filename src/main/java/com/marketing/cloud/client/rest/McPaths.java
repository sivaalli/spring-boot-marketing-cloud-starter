package com.marketing.cloud.client.rest;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public final class McPaths {

    public static final String V2_TOKEN = "/v2/token";
    public static final String SOAP_SERVICE = "/Service.asmx";
    public static final String POST_DATAEVENTS = "/hub/v1/dataevents/{dataExtensionId}/rowset";

    private McPaths() {
    }

    public static URI getUri(String baseUrl, String tenantId, String path) {
        try {
            return new URIBuilder(String.format(baseUrl, tenantId)).setPath(path).build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
