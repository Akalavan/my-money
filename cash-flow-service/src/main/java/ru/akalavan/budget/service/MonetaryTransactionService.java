package ru.akalavan.budget.service;

import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.entity.MonetaryTransaction;
import ru.akalavan.budget.entity.TypeOperation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MonetaryTransactionService {

    MonetaryTransaction createMonetaryTransaction(String name, String description, BigDecimal amount,
                                                  Category category, TypeOperation typeOperation, LocalDateTime dateOperation);

    public Iterable<MonetaryTransaction> findAllMonetaryTransaction();
}
