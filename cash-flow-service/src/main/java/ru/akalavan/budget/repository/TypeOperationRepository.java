package ru.akalavan.budget.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.akalavan.budget.entity.TypeOperation;

@Repository
public interface TypeOperationRepository extends CrudRepository<TypeOperation, Integer> {
}
