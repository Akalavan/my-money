package ru.akalavan.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.akalavan.budget.controller.payload.NewCategoryPayload;
import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.service.CategoryService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("cash-flow-api/categories")
public class CategoriesRestController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody NewCategoryPayload payload,
                                            BindingResult bindingResult,
                                            UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Category category = categoryService.createCategory(payload.name(), payload.description());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("cash-flow-api/categories/{categoryId}")
                            .build(Map.of("categoryId", category.getId())))
                    .body(category);
        }
    }

    @GetMapping
    public Iterable<Category> findAllCategory() {
        return categoryService.findAllCategories();
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent()
                .build();
    }
}
