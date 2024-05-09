package ru.akalavan.my_money.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.akalavan.my_money.client.BadRequestException;
import ru.akalavan.my_money.client.TypeOperationsRestClient;
import ru.akalavan.my_money.controller.payload.UpdateTypeOperationPayload;
import ru.akalavan.my_money.entity.TypeOperation;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("cash-flow/type-operations/{typeOperationId:\\d+}")
@RequiredArgsConstructor
public class TypeOperationController {

    private final TypeOperationsRestClient typeOperationsRestClient;

    @ModelAttribute("typeOperation")
    public TypeOperation typeOperation(@PathVariable("typeOperationId") int typeOperationId) {
        return typeOperationsRestClient.findById(typeOperationId)
                .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.type_operations.not_found"));
    }

    @GetMapping()
    public String getTypeOperation() {
        return "cash-flow/type-operations/type_operation";
    }

    @GetMapping("edit")
    public String getTypeOperationEditPage() {
        return "cash-flow/type-operations/edit";
    }

    @PostMapping("edit")
    public String updateTypeOperation(@ModelAttribute("typeOperation") TypeOperation typeOperation,
                                      UpdateTypeOperationPayload payload,
                                      Model model) {
        try {
            typeOperationsRestClient.updateTypeOperation(typeOperation.id(), payload.name(), payload.description());
            return "redirect:/cash-flow/type-operations/%d".formatted(typeOperation.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "cash-flow/type-operations/edit";
        }
    }

}
