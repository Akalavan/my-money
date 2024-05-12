package ru.akalavan.my_money.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.akalavan.my_money.controller.payload.NewCategoryPayload;
import ru.akalavan.my_money.controller.payload.UpdateCategoryPayload;
import ru.akalavan.my_money.entity.Category;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientCategoriesRestClient implements CategoriesRestClient {

    private final ParameterizedTypeReference<List<Category>> CATEGORIES_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public List<Category> findAllCategory() {
        return restClient
                .get()
                .uri("cash-flow-api/categories")
                .retrieve()
                .body(CATEGORIES_TYPE_REFERENCE);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        try {
            return Optional.ofNullable(restClient
                    .get()
                    .uri("cash-flow-api/categories/{categoryId}", id)
                    .retrieve()
                    .body(Category.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }

    }

    @Override
    public Category createCategory(String name, String description) {
        try {
            return restClient
                    .post()
                    .uri("cash-flow-api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewCategoryPayload(name, description))
                    .retrieve()
                    .body(Category.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void updateCategory(Integer id, String name, String description) {
        try {
            restClient
                    .patch()
                    .uri("cash-flow-api/categories/{categoryId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateCategoryPayload(name, description))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }
}
