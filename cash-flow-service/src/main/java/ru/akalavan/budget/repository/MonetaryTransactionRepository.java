package ru.akalavan.budget.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.akalavan.budget.entity.MonetaryTransaction;

public interface MonetaryTransactionRepository extends CrudRepository<MonetaryTransaction, Integer> {

    @Query(value = "select * from monetary_transactions where CAST(TO_CHAR(date_operation, 'MM') AS integer) = :month", nativeQuery = true)
    Iterable<MonetaryTransaction> findByDateOperation(@Param("month") int month);
}
