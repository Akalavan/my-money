package ru.akalavan.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.entity.MonetaryTransaction;
import ru.akalavan.budget.entity.TypeOperation;
import ru.akalavan.budget.repository.CategoryRepository;
import ru.akalavan.budget.repository.MonetaryTransactionRepository;
import ru.akalavan.budget.repository.TypeOperationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultMonetaryTransactionService implements MonetaryTransactionService {

    private final MonetaryTransactionRepository monetaryTransactionRepository;

    @Override
    public MonetaryTransaction createMonetaryTransaction(String name, String description, BigDecimal amount,
                                                         Category category, TypeOperation typeOperation, LocalDateTime dateOperation) {
        var monetaryTransaction = new MonetaryTransaction(null, name, description, amount, category, typeOperation, dateOperation);
        return monetaryTransactionRepository.save(monetaryTransaction);
    }

    @Override
    public Iterable<MonetaryTransaction> findAllMonetaryTransaction() {
        return monetaryTransactionRepository.findAll();
    }
}
