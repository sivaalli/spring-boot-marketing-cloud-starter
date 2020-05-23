package com.marketing.cloud.client.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URISyntaxException;

public class FuelHeaderCallback implements WebServiceMessageCallback {

    private static final char DOT = '.';
    private static final Logger logger = LoggerFactory.getLogger(FuelHeaderCallback.class);
    private static final String AUTH_HEADER = "fueloauth";

    private final Authenticator authenticator;

    public FuelHeaderCallback(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
        if (message instanceof SoapMessage) {
            final SoapMessage soapMessage = (SoapMessage) message;
            final QName fuelAuthHeader = new QName(AUTH_HEADER);
            final SoapHeaderElement soapHeaderElement = soapMessage.getSoapHeader().addHeaderElement(fuelAuthHeader);

            // a hack to get the connection
            final TransportContext transportContext = TransportContextHolder.getTransportContext();
            try {
                final String host = transportContext.getConnection().getUri().getHost();
                final String tenantId = host.substring(0, host.indexOf(DOT));

                soapHeaderElement.setText(authenticator.authenticate(tenantId).getAccessToken());

            } catch (URISyntaxException e) {
                logger.error("Cannot parse marketing cloud tenant Id from {}", e.getInput(), e);
            }
        }
    }
}
