package ru.akalavan.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.entity.MonetaryTransaction;
import ru.akalavan.budget.entity.TypeOperation;
import ru.akalavan.budget.repository.MonetaryTransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultMonetaryTransactionService implements MonetaryTransactionService {

    private final MonetaryTransactionRepository monetaryTransactionRepository;
    private final CategoryService categoryService;
    private final TypeOperationService typeOperationService;

    @Override
    @Transactional
    public MonetaryTransaction createMonetaryTransaction(String name, String description, BigDecimal amount,
                                                         Category category, TypeOperation typeOperation, LocalDateTime dateOperation) {
        var monetaryTransaction = new MonetaryTransaction(null, name, description, amount, category, typeOperation, dateOperation);
        return monetaryTransactionRepository.save(monetaryTransaction);
    }

    @Override
    public Iterable<MonetaryTransaction> findAllMonetaryTransaction() {
        return monetaryTransactionRepository.findAll();
    }

    @Override
    public Optional<MonetaryTransaction> findById(Integer id) {
        return monetaryTransactionRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateMonetaryTransaction(Integer id, String name, String description, BigDecimal amount,
                                          Integer categoryId, Integer typeOperationId, LocalDateTime localDateTime) {
        MonetaryTransaction monetaryTransaction = monetaryTransactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.monetary_transactions.not_found"));

        monetaryTransaction.setName(name);
        monetaryTransaction.setDescription(description);
        monetaryTransaction.setDateOperation(localDateTime);

        categoryService.findById(categoryId)
                .ifPresent(monetaryTransaction::setCategory);
        typeOperationService.findById(typeOperationId)
                .ifPresent(monetaryTransaction::setTypeOperation);

        if (!amount.equals(BigDecimal.ZERO)) {
            monetaryTransaction.setAmount(amount);
        }

    }
}
