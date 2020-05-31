package com.marketing.cloud.request.status;

import com.google.common.base.Preconditions;
import com.marketing.cloud.client.BaseRequestResponse;
import com.marketing.cloud.response.RequestProtocol;
import com.marketing.cloud.wsdl.SystemStatusOptions;

public class SystemStatusRequest extends BaseRequestResponse {

    private final SystemStatusOptions systemStatusOptions;

    public SystemStatusRequest(RequestProtocol protocol, String tenantId, SystemStatusOptions systemStatusOptions) {
        super(protocol, tenantId);
        this.systemStatusOptions = systemStatusOptions;
    }

    public SystemStatusOptions getSystemStatusOptions() {
        return systemStatusOptions;
    }

    public static class Builder {
        private RequestProtocol protocol;
        private String tenantId;

        public Builder setProtocol(RequestProtocol protocol) {
            Preconditions.checkArgument(protocol == RequestProtocol.SOAP, "SystemStatusRequest ONLY supports SOAP");
            this.protocol = protocol;
            return this;
        }

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public SystemStatusRequest build() {
            return new SystemStatusRequest(protocol, tenantId, new SystemStatusOptions());
        }
    }
}
