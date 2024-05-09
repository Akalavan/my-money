package ru.akalavan.my_money.client;

import ru.akalavan.my_money.entity.TypeOperation;

import java.util.List;
import java.util.Optional;

public interface TypeOperationsRestClient {

    List<TypeOperation> findAllTypeOperation();

    Optional<TypeOperation> findById(Integer id);

    TypeOperation createTypeOperation(String name, String description) throws BadRequestException;

    void updateTypeOperation(Integer id, String name, String description);
}
