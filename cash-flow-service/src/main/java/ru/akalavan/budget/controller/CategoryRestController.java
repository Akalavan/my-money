package ru.akalavan.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akalavan.budget.controller.payload.UpdateCategoryPayload;
import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.service.CategoryService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("cash-flow-api/categories/{categoryId:\\d+}")
public class CategoryRestController {

    private final CategoryService categoryService;

    @ModelAttribute("category")
    public Category getCategory(@PathVariable("categoryId") int categoriesId) {
        return categoryService.findById(categoriesId)
                .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.category.not_found"));
    }

    @GetMapping
    public Category findCategory(@ModelAttribute("category") Category category) {
        return category;
    }

    @PatchMapping
    public ResponseEntity<Void> updateCategory(@PathVariable("categoryId") int categoryId,
                                               @Valid @RequestBody UpdateCategoryPayload payload,
                                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            categoryService.updateCategory(categoryId, payload.name(), payload.description());
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent()
                .build();
    }
}
