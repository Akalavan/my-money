package ru.akalavan.my_money.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.akalavan.my_money.controller.payload.NewTypeOperationPayload;
import ru.akalavan.my_money.controller.payload.UpdateTypeOperationPayload;
import ru.akalavan.my_money.entity.TypeOperation;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientTypeOperationsRestClient implements TypeOperationsRestClient {

    private final ParameterizedTypeReference<List<TypeOperation>> TYPE_OPERATIONS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public List<TypeOperation> findAllTypeOperation() {
        return restClient
                .get()
                .uri("cash-flow-api/type-operations")
                .retrieve()
                .body(TYPE_OPERATIONS_TYPE_REFERENCE);
    }

    @Override
    public Optional<TypeOperation> findById(Integer id) {
        try {
            return Optional.ofNullable(restClient
                    .get()
                    .uri("cash-flow-api/type-operations/{typeOperationsId}", id)
                    .retrieve()
                    .body(TypeOperation.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }

    }

    @Override
    public TypeOperation createTypeOperation(String name, String description) {
        try {
            return restClient
                    .post()
                    .uri("cash-flow-api/type-operations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewTypeOperationPayload(name, description))
                    .retrieve()
                    .body(TypeOperation.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void updateTypeOperation(Integer id, String name, String description) {
        try {
            restClient
                    .patch()
                    .uri("cash-flow-api/type-operations/{typeOperationsId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateTypeOperationPayload(name, description))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }
}
