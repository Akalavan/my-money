package ru.akalavan.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.akalavan.budget.controller.payload.NewMonetaryTransactionPayload;
import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.entity.MonetaryTransaction;
import ru.akalavan.budget.entity.TypeOperation;
import ru.akalavan.budget.service.CategoryService;
import ru.akalavan.budget.service.MonetaryTransactionService;
import ru.akalavan.budget.service.TypeOperationService;
import ru.akalavan.budget.service.import_pdf.PDFReader;
import ru.akalavan.budget.service.uploadingfiles.StorageService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("cash-flow-api/monetary-transactions")
public class MonetaryTransactionsRestController {

    private final MonetaryTransactionService monetaryTransactionService;
    private final CategoryService categoryService;
    private final TypeOperationService typeOperationService;
    private final PDFReader pdfReader;
    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<MonetaryTransaction> createMonetaryTransaction(@Valid @RequestBody NewMonetaryTransactionPayload payload,
                                                                         BindingResult bindingResult,
                                                                         UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Optional<Category> category = categoryService.findById(payload.categoryId());
            Optional<TypeOperation> typeOperation = typeOperationService.findById(payload.typeOperationId());
            // TODO: deserialize LocalDateTime
            var monetaryTransaction = monetaryTransactionService.createMonetaryTransaction(
                    payload.name(), payload.description(), payload.amount(),
                    category.get(), typeOperation.get(), payload.dateOperation()
            );

            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("cash-flow-api/monetary-transaction/{monetaryTransactionId}")
                            .build(Map.of("monetaryTransactionId", monetaryTransaction.getId())))
                    .body(monetaryTransaction);
        }
    }

    @GetMapping
    public Iterable<MonetaryTransaction> findAllMonetaryTransaction(@RequestParam(name = "filter", required = false) String filter) {
        return monetaryTransactionService.findAllMonetaryTransaction(filter);
    }

    @PostMapping("import")
    public Iterable<MonetaryTransaction> getMonetaryTransactionsFromPDF(@RequestParam("pdfFile") MultipartFile file) {
        storageService.init();
        storageService.store(file);
        return pdfReader.readPdf(storageService.load(file.getOriginalFilename()).toString());
    }
}
