package ru.akalavan.budget.service;

import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.entity.MonetaryTransaction;
import ru.akalavan.budget.entity.TypeOperation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface MonetaryTransactionService {

    // TODO: Возможно тут лучше вместо category и typeOperation передавать их id и уже в сервисе получать объекты
    MonetaryTransaction createMonetaryTransaction(String name, String description, BigDecimal amount,
                                                  Category category, TypeOperation typeOperation, LocalDateTime dateOperation);

    Iterable<MonetaryTransaction> findAllMonetaryTransaction(String name, Optional<Integer> category_id, Optional<Integer> type_operation_id,
                                                             LocalDateTime dateOperationStart, LocalDateTime dateOperationEnd);

    Optional<MonetaryTransaction> findById(Integer id);

    void updateMonetaryTransaction(Integer id, String name, String description, BigDecimal amount,
                                   Integer categoryId, Integer typeOperationId, LocalDateTime localDateTime);
}
