package com.marketing.cloud.client.rest;

import com.google.common.base.Preconditions;
import com.marketing.cloud.client.config.McConfigurationProperties;
import com.marketing.cloud.request.dataextention.DataExtensionRequest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class BaseRestClient {

    private final RestTemplate restTemplate;
    private final McConfigurationProperties config;

    public BaseRestClient(RestTemplate restTemplate, McConfigurationProperties config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    /**
     * Inserts the rows keys and values given in {@linkplain DataExtensionRequest} does not exists or else
     * updates the row based on the keys given.
     * @param request the request containing info about data extension.
     */
    public void insertOrUpdate(String tenantId, DataExtensionRequest request) throws URISyntaxException {
        Preconditions.checkArgument(request != null, "request is required.");
        Preconditions.checkArgument(tenantId != null, "tenantId required.");

        final String restBaseUrl = config.getRestBaseUrl();
        final String tenantRestBaseUrl = String.format(restBaseUrl, tenantId);
        final URI dataExtensionUri = new URI(tenantRestBaseUrl + McPaths.POST_DATAEVENTS);

        restTemplate.postForObject(dataExtensionUri, toRestEntity(request), null);
    }

    private static Object toRestEntity(DataExtensionRequest request) {
        return null;
    }

    static final class DataExtensionModel {

        private List<DataExtensionRow> dataExtensionRows;

        public List<DataExtensionRow> getDataExtensionRows() {
            return dataExtensionRows;
        }

        public void setDataExtensionRows(List<DataExtensionRow> dataExtensionRows) {
            this.dataExtensionRows = dataExtensionRows;
        }

        static final class DataExtensionRow {
            private Map<String, String> keys;
            private Map<String, String> values;

            public Map<String, String> getKeys() {
                return keys;
            }

            public void setKeys(Map<String, String> keys) {
                this.keys = keys;
            }

            public Map<String, String> getValues() {
                return values;
            }

            public void setValues(Map<String, String> values) {
                this.values = values;
            }
        }

    }
}
