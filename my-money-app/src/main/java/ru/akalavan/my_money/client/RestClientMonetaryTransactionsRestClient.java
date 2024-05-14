package ru.akalavan.my_money.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.akalavan.my_money.controller.payload.NewMonetaryTransactionPayload;
import ru.akalavan.my_money.controller.payload.UpdateMonetaryTransactionPayload;
import ru.akalavan.my_money.entity.Category;
import ru.akalavan.my_money.entity.MonetaryTransaction;
import ru.akalavan.my_money.entity.TypeOperation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientMonetaryTransactionsRestClient implements MonetaryTransactionsRestClient {

    private final ParameterizedTypeReference<List<MonetaryTransaction>> MONETARY_TRANSACTIONS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public MonetaryTransaction createMonetaryTransaction(String name, String description, BigDecimal amount, Category category, TypeOperation typeOperation, LocalDateTime dateOperation) {
        try {
            return restClient.post()
                    .uri("cash-flow-api/monetary-transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewMonetaryTransactionPayload(name, description, amount, category.id(), typeOperation.id(), dateOperation))
                    .retrieve()
                    .body(MonetaryTransaction.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public List<MonetaryTransaction> findAllMonetaryTransaction() {
        return restClient.get()
                .uri("cash-flow-api/monetary-transactions")
                .retrieve()
                .body(MONETARY_TRANSACTIONS_TYPE_REFERENCE);
    }

    @Override
    public Optional<MonetaryTransaction> findById(Integer id) {
        try {
            return Optional.ofNullable(restClient.get()
                    .uri("cash-flow-api/monetary-transactions/{monetaryTransactionId}", id)
                    .retrieve()
                    .body(MonetaryTransaction.class));
        } catch (HttpClientErrorException.BadRequest exception) {
            return Optional.empty();
        }

    }

    @Override
    public void updateMonetaryTransaction(Integer id, String name, String description, BigDecimal amount, Integer categoryId, Integer typeOperationId, LocalDateTime dateOperation) {
        try {
            restClient.patch()
                    .uri("cash-flow-api/monetary-transactions/{monetaryTransactionId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateMonetaryTransactionPayload(name, description, amount, categoryId, typeOperationId, dateOperation))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }
}
