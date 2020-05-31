package com.marketing.cloud.client.rest;

import com.marketing.cloud.client.Client;

public class RestClient implements Client {

    private final BaseRestClient restClient;

    public RestClient(BaseRestClient restClient) {
        this.restClient = restClient;
    }
}
