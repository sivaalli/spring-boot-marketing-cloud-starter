package com.marketing.cloud.response.dataextension;

import com.google.common.base.MoreObjects;
import com.marketing.cloud.client.BaseRequestResponse;
import com.marketing.cloud.response.RequestProtocol;
import com.marketing.cloud.response.Status;

public class DataExtensionResponse extends BaseRequestResponse {

    private Status status;

    public DataExtensionResponse(RequestProtocol protocol, String tenantId) {
        super(protocol, tenantId);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .toString();
    }
}
