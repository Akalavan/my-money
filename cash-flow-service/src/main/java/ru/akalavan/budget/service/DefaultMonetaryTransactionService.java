package ru.akalavan.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    public Iterable<MonetaryTransaction> findAllMonetaryTransaction(String name, Optional<Integer> categoryId, Optional<Integer> typeOperationId,
                                                                    LocalDateTime dateOperationStart, LocalDateTime dateOperationEnd) {
        return monetaryTransactionRepository.findAll(
                Specification.allOf(
                        this.getMonetaryTransactionByName(name),
                        this.getMonetaryTransactionByCategory(categoryId),
                        this.getMonetaryTransactionByTypeOperation(typeOperationId),
                        this.getMonetaryTransactionByDateOperation(dateOperationStart, dateOperationEnd)
                )
        );
    }

    private Specification<MonetaryTransaction> getMonetaryTransactionByName(String name) {
        return (root, query, criteriaBuilder) ->
                !StringUtils.hasText(name) ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    private Specification<MonetaryTransaction> getMonetaryTransactionByCategory(Optional<Integer> categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId.map(category -> criteriaBuilder.equal(
                        root.get("category").get("id"), category
                )).orElse(null);
    }

    private Specification<MonetaryTransaction> getMonetaryTransactionByTypeOperation(Optional<Integer> typeOperationId) {
        return (root, query, criteriaBuilder) ->
                typeOperationId.map(category -> criteriaBuilder.equal(
                        root.get("typeOperation").get("id"), category
                )).orElse(null);
    }

    private Specification<MonetaryTransaction> getMonetaryTransactionByDateOperation(LocalDateTime dateOperationStart, LocalDateTime dateOperationEnd) {
        if (dateOperationStart != null && dateOperationEnd != null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("dateOperation").as(LocalDateTime.class), dateOperationStart, dateOperationEnd);
        } else if (dateOperationStart != null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateOperation").as(LocalDateTime.class), dateOperationStart);
        } else if (dateOperationEnd != null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateOperation").as(LocalDateTime.class), dateOperationEnd);
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
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
