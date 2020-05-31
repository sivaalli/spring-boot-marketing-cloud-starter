package com.marketing.cloud.request.dataextention;

import com.marketing.cloud.client.BaseRequestResponse;
import com.marketing.cloud.response.RequestProtocol;

import java.util.Map;

public class DataExtensionRequest extends BaseRequestResponse {

    private final Map<String, String> keys;
    private final Map<String, String> values;
    private final String externalKey;

    private DataExtensionRequest(RequestProtocol protocol, String tenantId, Map<String, String> keys, Map<String, String> values, String externalKey) {
        super(protocol, tenantId);
        this.keys = keys;
        this.values = values;
        this.externalKey = externalKey;
    }

    public Map<String, String> getKeys() {
        return keys;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public String getExternalKey() {
        return externalKey;
    }

    public static class Builder {
        private Map<String, String> keys;
        private Map<String, String> values;
        private String externalKey;
        private RequestProtocol protocol;
        private String tenantId;

        public Builder setKeys(Map<String, String> keys) {
            this.keys = keys;
            return this;
        }

        public Builder setValues(Map<String, String> values) {
            this.values = values;
            return this;
        }

        public Builder setExternalKey(String externalKey) {
            this.externalKey = externalKey;
            return this;
        }

        public Builder setProtocol(RequestProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public DataExtensionRequest build() {
            if (protocol == null) {
                setProtocol(findBestProtocol());
            }

            return new DataExtensionRequest(protocol, tenantId, keys, values, externalKey);
        }

        private RequestProtocol findBestProtocol() {
            // todo
            return RequestProtocol.REST;
        }
    }

}
