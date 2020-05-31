package com.marketing.cloud.request;

import com.marketing.cloud.request.dataextention.DataExtensionRequest;
import com.marketing.cloud.request.status.SystemStatusRequest;

public final class Requests {

    private Requests() {
    }

    public static DataExtensionRequest.Builder dataExtension() {
        return new DataExtensionRequest.Builder();
    }

    public static SystemStatusRequest.Builder systemStatusRequest() {
        return new SystemStatusRequest.Builder();
    }
}
