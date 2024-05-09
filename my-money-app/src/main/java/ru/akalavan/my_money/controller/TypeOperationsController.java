package ru.akalavan.my_money.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akalavan.my_money.client.BadRequestException;
import ru.akalavan.my_money.client.TypeOperationsRestClient;
import ru.akalavan.my_money.controller.payload.NewTypeOperationPayload;
import ru.akalavan.my_money.entity.TypeOperation;

@Controller
@RequestMapping("cash-flow/type-operations")
@RequiredArgsConstructor
public class TypeOperationsController {

    private final TypeOperationsRestClient typeOperationsRestClient;

    @GetMapping("list")
    public String getTypeOperationsList(Model model) {
        model.addAttribute("typeOperations", typeOperationsRestClient.findAllTypeOperation());
        return "cash-flow/type-operations/list";
    }

    @GetMapping("create")
    public String getNewTypeOperationsPage() {
        return "cash-flow/type-operations/create";
    }

    @PostMapping("create")
    public String createTypeOperation(NewTypeOperationPayload payload,
                                      Model model) {
        try {
            TypeOperation typeOperation = typeOperationsRestClient.createTypeOperation(payload.name(), payload.description());
            return "redirect:/cash-flow/type-operations/%d".formatted(typeOperation.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "cash-flow/type-operations/create";
        }
    }
}
