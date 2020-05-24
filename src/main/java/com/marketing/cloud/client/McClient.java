package com.marketing.cloud.client;

import com.marketing.cloud.wsdl.SystemStatusRequestMsg;
import com.marketing.cloud.wsdl.SystemStatusResponseMsg;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

// https://YOUR_SUBDOMAIN.soap.marketingcloudapis.com/Service.asmx
public class McClient extends WebServiceGatewaySupport {

    private static final String MC_SOAP_ENDPOINT = "";

    private static final WebServiceMessageCallback SYSTEM_STATUS_SOAP_ACTION = new SoapActionCallback("GetSystemStatus");

    public SystemStatusResponseMsg systemStatus(String soapBaseUrl, SystemStatusRequestMsg requestMsg) {
        return (SystemStatusResponseMsg) getWebServiceTemplate().marshalSendAndReceive(soapBaseUrl, requestMsg, SYSTEM_STATUS_SOAP_ACTION);
    }

    public SystemStatusResponseMsg systemStatus(SystemStatusRequestMsg requestMsg) {
        return (SystemStatusResponseMsg) getWebServiceTemplate().marshalSendAndReceive(requestMsg, SYSTEM_STATUS_SOAP_ACTION);
    }
}
