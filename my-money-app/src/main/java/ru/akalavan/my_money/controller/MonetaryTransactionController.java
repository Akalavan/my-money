package ru.akalavan.my_money.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.akalavan.my_money.client.BadRequestException;
import ru.akalavan.my_money.client.CategoriesRestClient;
import ru.akalavan.my_money.client.MonetaryTransactionsRestClient;
import ru.akalavan.my_money.client.TypeOperationsRestClient;
import ru.akalavan.my_money.controller.payload.NewMonetaryTransactionPayload;
import ru.akalavan.my_money.entity.MonetaryTransaction;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("cash-flow/monetary-transactions/{monetaryTransactionId:\\d+}")
@RequiredArgsConstructor
public class MonetaryTransactionController {

    private final MonetaryTransactionsRestClient monetaryTransactionsRestClient;
    private final CategoriesRestClient categoriesRestClient;
    private final TypeOperationsRestClient typeOperationsRestClient;

    @ModelAttribute("monetaryTransaction")
    public MonetaryTransaction monetaryTransaction(@PathVariable("monetaryTransactionId") int monetaryTransactionId) {
        return monetaryTransactionsRestClient.findById(monetaryTransactionId)
                .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.monetary_transactions.not_found"));
    }

    @GetMapping()
    public String getMonetaryTransactionsPage() {
        return "cash-flow/monetary-transactions/monetary_transaction";
    }

    @GetMapping("edit")
    public String getMonetaryTransactionsEditPage(Model model) {
        model.addAttribute("categories", categoriesRestClient.findAllCategory());
        model.addAttribute("typeOperations", typeOperationsRestClient.findAllTypeOperation());
        return "cash-flow/monetary-transactions/edit";
    }

    @PostMapping("edit")
    public String createMonetaryTransactions(@ModelAttribute("monetaryTransaction") MonetaryTransaction monetaryTransaction,
                                             NewMonetaryTransactionPayload payload,
                                             Model model) {
        try {
            monetaryTransactionsRestClient.updateMonetaryTransaction(
                    monetaryTransaction.id(), payload.name(), payload.description(), payload.amount(),
                    payload.categoryId(), payload.typeOperationId(), payload.dateOperation());
            return "redirect:/cash-flow/monetary-transactions/%d".formatted(monetaryTransaction.id());
        } catch (BadRequestException exception) {
            model.addAttribute("categories", categoriesRestClient.findAllCategory());
            model.addAttribute("typeOperations", typeOperationsRestClient.findAllTypeOperation());
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "cash-flow/monetary-transactions/edit";
        }

    }
}
