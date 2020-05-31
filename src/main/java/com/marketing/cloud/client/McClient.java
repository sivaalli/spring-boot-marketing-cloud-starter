package com.marketing.cloud.client;

import com.marketing.cloud.request.status.SystemStatusRequest;
import com.marketing.cloud.response.SystemStatusResponse;

public class McClient {

    private final Client restClient;
    private final Client soapClient;

    public McClient(Client restClient, Client soapClient) {
        this.restClient = restClient;
        this.soapClient = soapClient;
    }

    /**
     * SystemStatus is only supported by SOAP.
     */
    public SystemStatusResponse execute(SystemStatusRequest systemStatusRequest) {
        return soapClient.systemStatus(systemStatusRequest);
    }
}
