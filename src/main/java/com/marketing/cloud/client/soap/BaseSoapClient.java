package com.marketing.cloud.client.soap;

import com.marketing.cloud.client.config.McConfigurationProperties;
import com.marketing.cloud.client.rest.McPaths;
import com.marketing.cloud.wsdl.SystemStatusRequestMsg;
import com.marketing.cloud.wsdl.SystemStatusResponseMsg;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.net.URI;
import java.net.URISyntaxException;

// https://YOUR_SUBDOMAIN.soap.marketingcloudapis.com/Service.asmx
public class BaseSoapClient extends WebServiceGatewaySupport {

    private static final WebServiceMessageCallback SYSTEM_STATUS_SOAP_ACTION = new SoapActionCallback("GetSystemStatus");
    private final McConfigurationProperties config;
    private final String defaultTenantId;

    public BaseSoapClient(McConfigurationProperties config) throws URISyntaxException {
        this.config = config;
        this.defaultTenantId = config.getDefaultTenantId();
    }

    public SystemStatusResponseMsg systemStatus(SystemStatusRequestMsg systemStatusRequestMsg) {
        return (SystemStatusResponseMsg) getWebServiceTemplate()
                .marshalSendAndReceive(systemStatusRequestMsg, SYSTEM_STATUS_SOAP_ACTION);
    }

    public SystemStatusResponseMsg systemStatus(String tenantId, SystemStatusRequestMsg systemStatusRequestMsg) {
        if (tenantId == null) {
            return systemStatus(systemStatusRequestMsg);
        }

        final URI soapServiceUri = McPaths.getUri(config.getSoapBaseUrl(), tenantId, McPaths.SOAP_SERVICE);
        return (SystemStatusResponseMsg) getWebServiceTemplate()
                .marshalSendAndReceive(soapServiceUri.toString(), systemStatusRequestMsg, SYSTEM_STATUS_SOAP_ACTION);
    }
}
