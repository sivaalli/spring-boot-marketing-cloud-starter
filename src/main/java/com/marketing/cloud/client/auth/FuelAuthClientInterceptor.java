package com.marketing.cloud.client.auth;

import com.marketing.cloud.client.TenantIdExtractor;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.support.interceptor.ClientInterceptorAdapter;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FuelAuthClientInterceptor extends ClientInterceptorAdapter {

    private static final String AUTH_HEADER = "fueloauth";

    private final Authenticator authenticator;
    private final TenantIdExtractor tenantIdExtractor;

    public FuelAuthClientInterceptor(Authenticator authenticator, TenantIdExtractor tenantIdExtractor) {
        this.authenticator = authenticator;
        this.tenantIdExtractor = tenantIdExtractor;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        final WebServiceMessage message = messageContext.getRequest();
        if (message instanceof SoapMessage) {
            final SoapMessage soapMessage = (SoapMessage) message;
            final QName fuelAuthHeader = new QName("auth", AUTH_HEADER);
            final SoapHeaderElement soapHeaderElement = soapMessage.getSoapHeader().addHeaderElement(fuelAuthHeader);

            // a hack to get the connection
            final TransportContext transportContext = TransportContextHolder.getTransportContext();
            try {
                final URI url = transportContext.getConnection().getUri();
                final String tenantId = tenantIdExtractor.extractTenantId(url);

                soapHeaderElement.setText(authenticator.authenticate(tenantId).getAccessToken());

            } catch (URISyntaxException e) {
                throw new WebServiceIOException("Cannot parse tenant id from " + e.getInput(), new IOException(e));
            } catch (AuthenticationException e) {
                throw new WebServiceIOException("Cannot authenticate with marketing cloud.", new IOException(e));
            }
        }
        return true;
    }
}
