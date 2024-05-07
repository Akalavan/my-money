package ru.akalavan.budget.repository;

import org.springframework.data.repository.CrudRepository;
import ru.akalavan.budget.entity.MonetaryTransaction;

public interface MonetaryTransactionRepository extends CrudRepository<MonetaryTransaction, Integer> {
}
