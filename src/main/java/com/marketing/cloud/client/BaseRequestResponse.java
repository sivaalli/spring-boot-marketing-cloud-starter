package com.marketing.cloud.client;

import com.marketing.cloud.response.RequestProtocol;

public abstract class BaseRequestResponse {
    private final RequestProtocol protocol;
    private final String tenantId;

    protected BaseRequestResponse(RequestProtocol protocol, String tenantId) {
        this.protocol = protocol;
        this.tenantId = tenantId;
    }

    public RequestProtocol getProtocol() {
        return protocol;
    }

    public String getTenantId() {
        return tenantId;
    }
}
