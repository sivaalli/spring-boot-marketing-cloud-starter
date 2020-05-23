package com.marketing.cloud.client;

import com.marketing.cloud.client.auth.FuelHeaderCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class McClient extends WebServiceGatewaySupport {

    private final FuelHeaderCallback fuelHeaderCallback;

    public McClient(FuelHeaderCallback fuelHeaderCallback) {
        this.fuelHeaderCallback = fuelHeaderCallback;
    }

    public void get(Object payload) {
        getWebServiceTemplate().marshalSendAndReceive(payload, fuelHeaderCallback);
    }
}
