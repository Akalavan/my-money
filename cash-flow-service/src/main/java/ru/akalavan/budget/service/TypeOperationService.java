package ru.akalavan.budget.service;

import org.springframework.stereotype.Service;
import ru.akalavan.budget.entity.TypeOperation;

import java.util.Optional;

@Service
public interface TypeOperationService {

    Iterable<TypeOperation> findAllTypeOperation();

    Optional<TypeOperation> findById(Integer id);

    TypeOperation createTypeOperation(String name, String description);

    void updateTypeOperation(Integer id, String name, String description);
}
