package ru.akalavan.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.akalavan.budget.controller.payload.NewMonetaryTransactionPayload;
import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.entity.MonetaryTransaction;
import ru.akalavan.budget.entity.TypeOperation;
import ru.akalavan.budget.service.CategoryService;
import ru.akalavan.budget.service.DefaultMonetaryTransactionService;
import ru.akalavan.budget.service.MonetaryTransactionService;
import ru.akalavan.budget.service.TypeOperationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("cash-flow-api/monetary-transactions")
public class MonetaryTransactionRestController {

    private final MonetaryTransactionService monetaryTransactionService;
    private final CategoryService categoryService;
    private final TypeOperationService typeOperationService;

    @PostMapping
    public ResponseEntity<MonetaryTransaction> createMonetaryTransaction(@Valid @RequestBody NewMonetaryTransactionPayload payload,
                                                                         BindingResult bindingResult,
                                                                         UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Optional<Category> category = categoryService.findById(payload.categoryId());
            Optional<TypeOperation> typeOperation = typeOperationService.findById(payload.typeOperationId());
            // TODO: deserialize LocalDateTime
            var monetaryTransaction = monetaryTransactionService.createMonetaryTransaction(
                    payload.name(), payload.description(), payload.amount(),
                    category.get(), typeOperation.get(), payload.dateOperation()
            );

            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("cash-flow-api/monetary-transaction/{monetaryTransactionId}")
                            .build(Map.of("monetaryTransactionId", monetaryTransaction.getId())))
                    .body(monetaryTransaction);
        }
    }

    @GetMapping
    public Iterable<MonetaryTransaction> findAllMonetaryTransaction() {
        return monetaryTransactionService.findAllMonetaryTransaction();
    }
}
