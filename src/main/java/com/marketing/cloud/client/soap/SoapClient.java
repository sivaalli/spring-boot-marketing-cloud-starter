package com.marketing.cloud.client.soap;

import com.google.common.base.Preconditions;
import com.marketing.cloud.McClientException;
import com.marketing.cloud.client.Client;
import com.marketing.cloud.request.status.SystemStatusRequest;
import com.marketing.cloud.response.RequestProtocol;
import com.marketing.cloud.response.SystemStatusResponse;
import com.marketing.cloud.wsdl.SystemStatusRequestMsg;
import com.marketing.cloud.wsdl.SystemStatusResponseMsg;

public class SoapClient implements Client {

    private final BaseSoapClient baseSoapClient;

    public SoapClient(BaseSoapClient baseSoapClient) {
        this.baseSoapClient = baseSoapClient;
    }

    @Override
    public SystemStatusResponse systemStatus(SystemStatusRequest request) {
        Preconditions.checkArgument(request.getProtocol() == RequestProtocol.SOAP, "SystemStatus does not support REST application protocol");

        SystemStatusResponseMsg responseMsg;
        try {
            responseMsg = baseSoapClient.systemStatus(request.getTenantId(), toSystemStatusRequestMsg(request));
        } catch (RuntimeException e) {
            throw new McClientException(e);
        }
        return SystemStatusResponse.from(responseMsg, request.getTenantId());
    }

    private static SystemStatusRequestMsg toSystemStatusRequestMsg(SystemStatusRequest request) {
        SystemStatusRequestMsg requestMsg = new SystemStatusRequestMsg();
        requestMsg.setOptions(request.getSystemStatusOptions());
        return requestMsg;
    }
}
