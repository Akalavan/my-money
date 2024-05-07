package ru.akalavan.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akalavan.budget.entity.TypeOperation;
import ru.akalavan.budget.repository.TypeOperationRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTypeOperationService implements TypeOperationService {

    private final TypeOperationRepository typeOperationRepository;

    @Override
    public Iterable<TypeOperation> findAllTypeOperation() {
        return typeOperationRepository.findAll();
    }

    @Override
    public Optional<TypeOperation> findById(Integer id) {
        return typeOperationRepository.findById(id);
    }

    @Override
    public TypeOperation createTypeOperation(String name, String description) {
        TypeOperation typeOperation = new TypeOperation(null, name, description);
        return typeOperationRepository.save(typeOperation);
    }
}
