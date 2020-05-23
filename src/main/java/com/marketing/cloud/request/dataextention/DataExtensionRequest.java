package com.marketing.cloud.request.dataextention;

import com.marketing.cloud.request.BaseRequest;

public class DataExtensionRequest extends BaseRequest {

    @Override
    public DataExtensionRequest baseUri(String baseUri) {
        super.baseUri(baseUri);
        return this;
    }
}
