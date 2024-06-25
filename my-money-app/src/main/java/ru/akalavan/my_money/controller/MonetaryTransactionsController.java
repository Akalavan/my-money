package ru.akalavan.my_money.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.akalavan.my_money.client.BadRequestException;
import ru.akalavan.my_money.client.CategoriesRestClient;
import ru.akalavan.my_money.client.MonetaryTransactionsRestClient;
import ru.akalavan.my_money.client.TypeOperationsRestClient;
import ru.akalavan.my_money.controller.payload.NewMonetaryTransactionPayload;
import ru.akalavan.my_money.dto.MonetaryTransactionDTO;
import ru.akalavan.my_money.entity.Category;
import ru.akalavan.my_money.entity.MonetaryTransaction;
import ru.akalavan.my_money.entity.MonetaryTransactionListContainer;
import ru.akalavan.my_money.entity.TypeOperation;
import ru.akalavan.my_money.sevice.MonetaryTransactionService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
@RequestMapping("cash-flow/monetary-transactions")
@RequiredArgsConstructor
public class MonetaryTransactionsController {

    private final MonetaryTransactionsRestClient monetaryTransactionsRestClient;
    private final CategoriesRestClient categoriesRestClient;
    private final TypeOperationsRestClient typeOperationsRestClient;
    private final MonetaryTransactionService monetaryTransactionService;

    @GetMapping("list")
    public String getMonetaryTransactionsListPage(@RequestParam(name = "category_id", required = false) Integer categoryId,
                                                  @RequestParam(name = "type_operation_id", required = false) Integer typeOperationId,
                                                  @RequestParam(name = "name", required = false) String name,
                                                  @RequestParam(name = "date_operation_start", required = false) String dateOperationStart,
                                                  @RequestParam(name = "date_operation_end", required = false) String dateOperationEnd,
                                                  Model model) {

        Map<String, Object> filter = Map.of(
                "categoryId", Objects.requireNonNullElse(categoryId, 0),
                "typeOperationId", Objects.requireNonNullElse(typeOperationId, 0),
                "name", Objects.requireNonNullElse(name, ""),
                "dateOperationStart", Objects.requireNonNullElse(dateOperationStart, ""),
                "dateOperationEnd", Objects.requireNonNullElse(dateOperationEnd, "")
        );
        model.addAttribute("monetaryTransactions", monetaryTransactionsRestClient
                .findAllMonetaryTransaction(name, categoryId, typeOperationId, dateOperationStart, dateOperationEnd));
        model.addAttribute("categories", categoriesRestClient.findAllCategory());
        model.addAttribute("typeOperations", typeOperationsRestClient.findAllTypeOperation());

        model.addAllAttributes(filter);
        return "cash-flow/monetary-transactions/list";
    }

    @GetMapping("create")
    public String getMonetaryTransactionsCreatePage(Model model) {
        model.addAttribute("categories", categoriesRestClient.findAllCategory());
        model.addAttribute("typeOperations", typeOperationsRestClient.findAllTypeOperation());
        return "cash-flow/monetary-transactions/create";
    }

    @PostMapping("create")
    public String createMonetaryTransactions(NewMonetaryTransactionPayload payload,
                                             Model model) {
        try {
            Category category = categoriesRestClient.findById(payload.categoryId())
                    .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.category.not_found"));
            TypeOperation typeOperation = typeOperationsRestClient.findById(payload.typeOperationId())
                    .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.type_operations.not_found"));

            MonetaryTransaction monetaryTransaction = monetaryTransactionsRestClient.createMonetaryTransaction(
                    payload.name(), payload.description(), payload.amount(),
                    category, typeOperation, payload.dateOperation());
            return "redirect:/cash-flow/monetary-transactions/%d".formatted(monetaryTransaction.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("error", exception.getErrors());
            return "cash-flow/monetary-transactions/create";
        }
    }

    @PostMapping("import")
    public String getMonetaryTransactionsFromPDF(@RequestParam("pdfFile") MultipartFile pdfFile, Model model) throws IOException {
        List<MonetaryTransactionDTO> transactionDTOList = monetaryTransactionsRestClient.getMonetaryTransactionsFromPDF(pdfFile)
                .stream()
                .map(mt -> new MonetaryTransactionDTO(mt.name(), mt.description(), mt.amount(), 0, mt.typeOperation().id(), mt.dateOperation()))
                .toList();

        var transactionContainer = new MonetaryTransactionListContainer(transactionDTOList);

        model.addAttribute("listMonetaryTransactions", transactionContainer);
        model.addAttribute("categories", categoriesRestClient.findAllCategory());
        model.addAttribute("typeOperations", typeOperationsRestClient.findAllTypeOperation());
        return "cash-flow/monetary-transactions/list_new";
    }

    @PostMapping("create-from-pdf")
    public String createMonetaryTransactionsFromPdf(@ModelAttribute MonetaryTransactionListContainer listMonetaryTransactions) {
        var newMonetaryTransactionPayloadStream = monetaryTransactionService
                .convertMonetaryTransactionDroToMonetaryTransactionPayload(listMonetaryTransactions.getMonetaryTransactions());
        monetaryTransactionService.saveMonetaryTransaction(newMonetaryTransactionPayloadStream);
        return "cash-flow/index";
    }

}
