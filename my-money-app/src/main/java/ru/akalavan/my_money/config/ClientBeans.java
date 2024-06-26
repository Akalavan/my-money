package ru.akalavan.my_money.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.akalavan.my_money.client.RestClientCategoriesRestClient;
import ru.akalavan.my_money.client.RestClientMonetaryTransactionsRestClient;
import ru.akalavan.my_money.client.RestClientTypeOperationsRestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientTypeOperationsRestClient typeOperationsRestClient(
            @Value("${cash-flow.service.uri:http://localhost:8081}") String cashFlowUri) {
        return new RestClientTypeOperationsRestClient(RestClient.builder()
                .baseUrl(cashFlowUri)
                .build());
    }

    @Bean
    public RestClientCategoriesRestClient categoriesRestClient(
            @Value("${cash-flow.service.uri:http://localhost:8081}") String cashFlowUri) {
        return new RestClientCategoriesRestClient(RestClient.builder()
                .baseUrl(cashFlowUri)
                .build());
    }

    @Bean
    public RestClientMonetaryTransactionsRestClient monetaryTransactionsRestClient(
            @Value("${cash-flow.service.uri:http://localhost:8081}") String cashFlowUri) {
        return new RestClientMonetaryTransactionsRestClient(RestClient.builder()
                .baseUrl(cashFlowUri)
                .build());
    }
}
