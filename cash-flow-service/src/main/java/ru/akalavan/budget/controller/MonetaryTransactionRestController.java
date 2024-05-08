package ru.akalavan.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akalavan.budget.controller.payload.UpdateMonetaryTransactionPayload;
import ru.akalavan.budget.entity.MonetaryTransaction;
import ru.akalavan.budget.service.MonetaryTransactionService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("cash-flow-api/monetary-transactions/{monetaryTransactionId:\\d+}")
public class MonetaryTransactionRestController {

    private final MonetaryTransactionService monetaryTransactionService;

    @ModelAttribute("monetaryTransaction")
    public MonetaryTransaction monetaryTransaction(@PathVariable("monetaryTransactionId") int monetaryTransactionId) {
        return monetaryTransactionService.findById(monetaryTransactionId)
                .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.monetary_transactions.not_found"));
    }

    @GetMapping
    public MonetaryTransaction findMonetaryTransaction(@ModelAttribute("monetaryTransaction") MonetaryTransaction monetaryTransaction) {
        return monetaryTransaction;
    }

    @PatchMapping
    public ResponseEntity<Void> updateMonetaryTransaction(@PathVariable("monetaryTransactionId") int monetaryTransactionId,
                                                          @Valid @RequestBody UpdateMonetaryTransactionPayload payload,
                                                          BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            monetaryTransactionService.updateMonetaryTransaction(monetaryTransactionId,
                    payload.name(), payload.description(), payload.amount(),
                    payload.categoryId(), payload.typeOperationId(), payload.dateOperation()
            );

            return ResponseEntity.noContent()
                    .build();
        }
    }
}
