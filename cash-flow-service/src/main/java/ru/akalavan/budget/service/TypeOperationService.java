package ru.akalavan.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akalavan.budget.entity.TypeOperation;

import java.util.Optional;

@Service
public interface TypeOperationService {

    Iterable<TypeOperation> findAllTypeOperation();

    Optional<TypeOperation> findById(Integer id);

    TypeOperation createTypeOperation(String name, String description);
}
