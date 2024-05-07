package ru.akalavan.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akalavan.budget.controller.payload.UpdateTypeOperationPayload;
import ru.akalavan.budget.entity.TypeOperation;
import ru.akalavan.budget.service.TypeOperationService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("cash-flow-api/type-operations/{typeOperationsId:\\d+}")
public class TypeOperationRestController {

    private final TypeOperationService typeOperationService;

    @ModelAttribute("typeOperations")
    public TypeOperation getTypeOperation(@PathVariable("typeOperationsId") int typeOperationsId) {
        return typeOperationService.findById(typeOperationsId)
                .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.typeOperations.not_found"));
    }

    @GetMapping
    public TypeOperation findTypeOperation(@ModelAttribute("typeOperations") TypeOperation typeOperation) {
        return typeOperation;
    }

    @PatchMapping
    public ResponseEntity<Void> updateTypeOperation(@PathVariable("typeOperationsId") int typeOperationsId,
                                                    @Valid @RequestBody UpdateTypeOperationPayload payload,
                                                    BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            typeOperationService.updateTypeOperation(typeOperationsId, payload.name(), payload.description());
            return ResponseEntity.noContent()
                    .build();
        }
    }

}
