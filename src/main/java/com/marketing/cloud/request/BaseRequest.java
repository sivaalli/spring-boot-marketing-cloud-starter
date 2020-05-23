package com.marketing.cloud.request;

import com.google.common.base.Preconditions;

public class BaseRequest {

    private String baseUri;

    public BaseRequest baseUri(String baseUri) {
        Preconditions.checkArgument(baseUri != null, "baseUri cannot be null");
        this.baseUri = baseUri;
        return this;
    }
}
