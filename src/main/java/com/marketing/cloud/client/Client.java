package com.marketing.cloud.client;

import com.marketing.cloud.request.status.SystemStatusRequest;
import com.marketing.cloud.response.SystemStatusResponse;

public interface Client {

    default SystemStatusResponse systemStatus(SystemStatusRequest request) {
        throw new RuntimeException("Not implemented");
    }
}
