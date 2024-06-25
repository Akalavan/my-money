package ru.akalavan.my_money.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.akalavan.my_money.controller.payload.NewMonetaryTransactionPayload;
import ru.akalavan.my_money.controller.payload.UpdateMonetaryTransactionPayload;
import ru.akalavan.my_money.entity.Category;
import ru.akalavan.my_money.entity.MonetaryTransaction;
import ru.akalavan.my_money.entity.TypeOperation;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
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
    public List<MonetaryTransaction> findAllMonetaryTransaction(String name, Integer categoryId, Integer typeOperationId,
                                                                String dateOperationStart, String dateOperationEnd) {
        URI uri = UriComponentsBuilder.fromPath("cash-flow-api/monetary-transactions")
                .queryParamIfPresent("name", Optional.ofNullable(name))
                .queryParamIfPresent("category_id", Optional.ofNullable(categoryId))
                .queryParamIfPresent("type_operation_id", Optional.ofNullable(typeOperationId))
                .queryParamIfPresent("date_operation_start", Optional.ofNullable(dateOperationStart))
                .queryParamIfPresent("date_operation_end", Optional.ofNullable(dateOperationEnd))
                .build()
                .toUri();

        return restClient.get()
                .uri(uri.toString())
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

    @Override
    public List<MonetaryTransaction> getMonetaryTransactionsFromPDF(MultipartFile file) throws IOException {
        Resource fileResource = file.getResource();
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("pdfFile", fileResource);

        return restClient.post()
                .uri("cash-flow-api/monetary-transactions/import")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(parts)
                .retrieve()
                .body(MONETARY_TRANSACTIONS_TYPE_REFERENCE);
    }

}
