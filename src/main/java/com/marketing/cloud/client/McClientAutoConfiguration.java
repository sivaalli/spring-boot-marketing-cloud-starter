package com.marketing.cloud.client;

import com.marketing.cloud.client.auth.AuthenticationManager;
import com.marketing.cloud.client.auth.McAuthenticator;
import com.marketing.cloud.client.config.McConfigurationProperties;
import com.marketing.cloud.client.rest.BaseRestClient;
import com.marketing.cloud.client.rest.FuelAuthRequestCustomizer;
import com.marketing.cloud.client.rest.McPaths;
import com.marketing.cloud.client.rest.RestClient;
import com.marketing.cloud.client.soap.BaseSoapClient;
import com.marketing.cloud.client.soap.FuelAuthClientInterceptor;
import com.marketing.cloud.client.soap.SoapClient;
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
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

@Configuration
@EnableConfigurationProperties(McConfigurationProperties.class)
public class McClientAutoConfiguration {

    public static final ClientInterceptor[] CLIENT_INTERCEPTORS = new ClientInterceptor[0];

    @Bean
    @ConditionalOnMissingBean
    public BaseSoapClient baseSoapClient(List<ClientInterceptor> interceptors, McConfigurationProperties config) throws URISyntaxException {
        final BaseSoapClient baseSoapClient = new BaseSoapClient(config);
        baseSoapClient.setInterceptors(interceptors.toArray(CLIENT_INTERCEPTORS));
        baseSoapClient.setDefaultUri(McPaths.getUri(config.getSoapBaseUrl(), config.getDefaultTenantId(), McPaths.SOAP_SERVICE).toString());
        baseSoapClient.setMarshaller(marshaller());
        baseSoapClient.setUnmarshaller(marshaller());
        baseSoapClient.setMessageSender(new HttpComponentsMessageSender(client()));
        return baseSoapClient;
    }

    @ConditionalOnMissingBean
    @Bean
    public SoapClient soapClient(BaseSoapClient baseSoapClient) {
        return new SoapClient(baseSoapClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public McClient mcClient(SoapClient soapClient, RestClient restClient) {
        return new McClient(restClient, soapClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public BaseRestClient baseRestClient(RestTemplate restTemplate, McConfigurationProperties config) {
        return new BaseRestClient(restTemplate, config);
    }

    @Bean(name = "restClient")
    @ConditionalOnMissingBean
    public RestClient restClient(BaseRestClient baseRestClient) {
        return new RestClient(baseRestClient);
    }

    public Jaxb2Marshaller marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.marketing.cloud.wsdl");
        return marshaller;
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientInterceptor fuelAuthClientInterceptor(AuthenticationManager mgr) {
        return new FuelAuthClientInterceptor(mgr, tenantIdExtractor());
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager(McConfigurationProperties config) throws URISyntaxException {
        return new AuthenticationManager(new McAuthenticator(config));
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate template(RestTemplateBuilder builder, FuelAuthRequestCustomizer fuelAuthRequestCustomizer) {
        return builder.setReadTimeout(Duration.ofSeconds(20))
                .setConnectTimeout(Duration.ofSeconds(20))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client()))
                .additionalRequestCustomizers(fuelAuthRequestCustomizer)
                .build();
    }

    @Bean
    public FuelAuthRequestCustomizer fuelAuthRequestCustomizer(AuthenticationManager authenticationManager) {
        return new FuelAuthRequestCustomizer(authenticationManager, tenantIdExtractor());
    }

    @Bean
    @ConditionalOnMissingBean
    public TenantIdExtractor tenantIdExtractor() {
        return new McTenantIdExtractor();
    }

    @Bean(destroyMethod = "close")
    public HttpClient client() {
        return HttpClients.custom()
                .evictExpiredConnections()
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .setMaxConnPerRoute(20)
                .setMaxConnTotal(60)
                .build();
    }
}
