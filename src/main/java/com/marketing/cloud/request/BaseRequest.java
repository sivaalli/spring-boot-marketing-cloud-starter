package com.marketing.cloud.request;

import com.google.common.base.Preconditions;
import com.marketing.cloud.client.McClient;

public class BaseRequest {

    private String baseUri;
    private McClient client;

    public BaseRequest baseUri(String baseUri) {
        Preconditions.checkArgument(baseUri != null, "baseUri cannot be null");
        this.baseUri = baseUri;
        return this;
    }
}
