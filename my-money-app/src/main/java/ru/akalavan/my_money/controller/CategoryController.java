package ru.akalavan.my_money.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.akalavan.my_money.client.BadRequestException;
import ru.akalavan.my_money.client.CategoriesRestClient;
import ru.akalavan.my_money.controller.payload.UpdateCategoryPayload;
import ru.akalavan.my_money.entity.Category;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("cash-flow/categories/{categoryId:\\d+}")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoriesRestClient categoriesRestClient;

    @ModelAttribute("category")
    public Category category(@PathVariable("categoryId") int typeOperationId) {
        return categoriesRestClient.findById(typeOperationId)
                .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.category.not_found"));
    }

    @GetMapping()
    public String getCategory() {
        return "cash-flow/categories/category";
    }

    @GetMapping("edit")
    public String getCategoryEditPage() {
        return "cash-flow/categories/edit";
    }

    @PostMapping("edit")
    public String updateCategory(@ModelAttribute("category") Category category,
                                      UpdateCategoryPayload payload,
                                      Model model) {
        try {
            categoriesRestClient.updateCategory(category.id(), payload.name(), payload.description());
            return "redirect:/cash-flow/categories/%d".formatted(category.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "cash-flow/categories/edit";
        }
    }

}
