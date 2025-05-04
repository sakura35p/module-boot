package com.side.rest.restclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.rest.exception.ApiException;
import com.side.rest.restclient.client.BaseClient;
import com.side.rest.restclient.factory.HttpInterfaceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class RestClientConfig {

    private final HttpInterfaceFactory httpInterfaceFactory;
    private final ObjectMapper objectMapper;

    private ClientHttpRequestFactory createRequestFactory() {

        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(5));
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(5));

        return simpleClientHttpRequestFactory;
    }

    private RestClient createRestClient(String baseUrl) {
        return RestClient.builder()
                         .baseUrl(baseUrl)
                         .requestFactory(createRequestFactory())
                         .messageConverters(
                                 converters -> {
                                     converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
                                     converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
                                 })
                         .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                         .defaultStatusHandler(
                                 HttpStatusCode::is4xxClientError,
                                 (request, response) -> {
                                     log.error("Client Error Code : {}", response.getStatusCode());
                                     log.error("Client Error Message : {}", new String(response.getBody()
                                                                                               .readAllBytes()));
                                     throw new ApiException(HttpStatus.valueOf(response.getStatusCode().value()));
                                 })
                         .defaultStatusHandler(
                                 HttpStatusCode::is5xxServerError,
                                 (request, response) -> {
                                     log.error("Server Error Code : {}", response.getStatusCode());
                                     log.error("Server Error Message : {}", new String(response.getBody()
                                                                                               .readAllBytes()));
                                     throw new ApiException(HttpStatus.valueOf(response.getStatusCode().value()));
                                 })
                         .build();
    }

    @Bean
    public BaseClient baseClient() throws URISyntaxException {
        RestClient restClient = this.createRestClient(new URI("").toASCIIString())
                                    .mutate()
                                    .defaultHeader("", "")
                                    .build();

        return httpInterfaceFactory.createClient(BaseClient.class, restClient);
    }
}
