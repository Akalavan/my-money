package ru.akalavan.my_money.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akalavan.my_money.client.BadRequestException;
import ru.akalavan.my_money.client.CategoriesRestClient;
import ru.akalavan.my_money.controller.payload.NewCategoryPayload;

@Controller
@RequestMapping("cash-flow/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesRestClient categoriesRestClient;

    @GetMapping("list")
    public String getCategoriesList(Model model) {
        model.addAttribute("categories", categoriesRestClient.findAllCategory());
        return "cash-flow/categories/list";
    }

    @GetMapping("create")
    public String getNewCategoriesPage() {
        return "cash-flow/categories/create";
    }

    @PostMapping("create")
    public String createCategory(NewCategoryPayload payload,
                                 Model model) {
        try {
            var category = categoriesRestClient.createCategory(payload.name(), payload.description());
            return "redirect:/cash-flow/categories/%d".formatted(category.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "cash-flow/categories/create";
        }
    }
}
