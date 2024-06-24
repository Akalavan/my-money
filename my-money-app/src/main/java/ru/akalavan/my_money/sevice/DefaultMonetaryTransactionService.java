package ru.akalavan.my_money.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akalavan.my_money.client.BadRequestException;
import ru.akalavan.my_money.client.CategoriesRestClient;
import ru.akalavan.my_money.client.MonetaryTransactionsRestClient;
import ru.akalavan.my_money.client.TypeOperationsRestClient;
import ru.akalavan.my_money.controller.payload.NewMonetaryTransactionPayload;
import ru.akalavan.my_money.dto.MonetaryTransactionDTO;
import ru.akalavan.my_money.entity.Category;
import ru.akalavan.my_money.entity.TypeOperation;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DefaultMonetaryTransactionService implements MonetaryTransactionService {

    private final MonetaryTransactionsRestClient monetaryTransactionsRestClient;
    private final CategoriesRestClient categoriesRestClient;
    private final TypeOperationsRestClient typeOperationsRestClient;

    @Override
    public Stream<NewMonetaryTransactionPayload> convertMonetaryTransactionDroToMonetaryTransactionPayload(List<MonetaryTransactionDTO> monetaryTransactionDTO) {
        return monetaryTransactionDTO.stream()
                .map(dto -> new NewMonetaryTransactionPayload(dto.getName(), dto.getDescription(), dto.getAmount(),
                        dto.getCategoryId(), dto.getTypeOperationId(), dto.getDateOperation()));
    }

    @Override
    public void saveMonetaryTransaction(Stream<NewMonetaryTransactionPayload> payloadStream) {
        payloadStream.forEach(payload -> {
            try {
                Category category = categoriesRestClient.findById(payload.categoryId())
                        .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.category.not_found"));
                TypeOperation typeOperation = typeOperationsRestClient.findById(payload.typeOperationId())
                        .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.type_operations.not_found"));

                monetaryTransactionsRestClient.createMonetaryTransaction(
                        payload.name(), payload.description(), payload.amount(),
                        category, typeOperation, payload.dateOperation());
            } catch (BadRequestException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public Map<Category, Double> getStatisticForMouth(int month) {
        return null;
    }
}
