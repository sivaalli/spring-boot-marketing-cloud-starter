package com.marketing.cloud.client;

import com.marketing.cloud.client.auth.AuthenticationManager;
import com.marketing.cloud.client.auth.FuelAuthClientInterceptor;
import com.marketing.cloud.client.auth.McAuthenticator;
import com.marketing.cloud.client.config.McConfigurationProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableConfigurationProperties(McConfigurationProperties.class)
public class McClientAutoConfiguration {


    public static final ClientInterceptor[] CLIENT_INTERCEPTORS = new ClientInterceptor[0];

    @Bean
    public McClient mcClient(List<ClientInterceptor> interceptors, McConfigurationProperties config) {
        final McClient mcClient = new McClient();
        mcClient.setInterceptors(interceptors.toArray(CLIENT_INTERCEPTORS));
        mcClient.setDefaultUri(String.format(config.getSoapServiceUrl(), config.getDefaultTenantId()));
        mcClient.setMarshaller(marshaller());
        mcClient.setUnmarshaller(marshaller());
        return mcClient;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.marketing.cloud.wsdl");
        return marshaller;
    }

    @Bean
    public ClientInterceptor fuelAuthClientInterceptor(AuthenticationManager mgr, TenantIdExtractor tenantIdExtractor) {
        return new FuelAuthClientInterceptor(mgr, tenantIdExtractor);
    }

    @Bean
    public AuthenticationManager authenticationManager(McConfigurationProperties config, RestTemplate restTemplate) {
        return new AuthenticationManager(new McAuthenticator(restTemplate, config));
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate template(RestTemplateBuilder builder, HttpClient client) {
        return builder.setReadTimeout(Duration.ofSeconds(10))
                .setConnectTimeout(Duration.ofSeconds(5))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public TenantIdExtractor tenantIdExtractor() {
        return new McTenantIdExtractor();
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public HttpClient client() {
        return HttpClients.custom()
                .evictExpiredConnections()
                .setMaxConnPerRoute(20)
                .setMaxConnTotal(60)
                .build();
    }
}
