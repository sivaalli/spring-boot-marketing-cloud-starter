package com.marketing.cloud.response;

import com.google.common.base.MoreObjects;
import com.marketing.cloud.client.BaseRequestResponse;
import com.marketing.cloud.wsdl.SystemStatusResponseMsg;

public class SystemStatusResponse extends BaseRequestResponse {

    private Status status;

    public SystemStatusResponse(RequestProtocol protocol, String tenantId, Status status) {
        super(protocol, tenantId);
    }

    public static SystemStatusResponse from(SystemStatusResponseMsg responseMsg, String tenantId) {
        return new SystemStatusResponse(RequestProtocol.SOAP, tenantId, Status.from(responseMsg.getOverallStatus()));
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
