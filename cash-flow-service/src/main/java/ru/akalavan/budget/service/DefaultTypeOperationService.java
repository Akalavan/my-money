package ru.akalavan.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akalavan.budget.entity.TypeOperation;
import ru.akalavan.budget.repository.TypeOperationRepository;

import java.util.NoSuchElementException;
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
    @Transactional
    public TypeOperation createTypeOperation(String name, String description) {
        TypeOperation typeOperation = new TypeOperation(null, name, description);
        return typeOperationRepository.save(typeOperation);
    }

    @Override
    @Transactional
    public void updateTypeOperation(Integer id, String name, String description) {
        typeOperationRepository.findById(id)
                .ifPresentOrElse(typeOperation -> {
                    typeOperation.setName(name);
                    typeOperation.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }
}
