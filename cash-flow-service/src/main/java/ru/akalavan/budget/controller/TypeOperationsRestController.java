package ru.akalavan.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.akalavan.budget.controller.payload.NewTypeOperationPayload;
import ru.akalavan.budget.entity.TypeOperation;
import ru.akalavan.budget.service.DefaultTypeOperationService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("cash-flow-api/type-operations")
public class TypeOperationsRestController {

    private final DefaultTypeOperationService typeOperationService;

    @PostMapping
    public ResponseEntity<TypeOperation> createTypeOperation(@Valid @RequestBody NewTypeOperationPayload payload,
                                                             BindingResult bindingResult,
                                                             UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            var typeOperation = typeOperationService.createTypeOperation(payload.name(), payload.description());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("cash-flow-api/type-operation/{typeOperationId}")
                            .build(Map.of("typeOperationId", typeOperation.getId())))
                    .body(typeOperation);
        }
    }

    @GetMapping
    public Iterable<TypeOperation> findAllTypeOperation() {
        return typeOperationService.findAllTypeOperation();
    }
}
