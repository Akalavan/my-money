package ru.akalavan.budget.service;

import org.springframework.stereotype.Service;
import ru.akalavan.budget.entity.Category;

import java.util.Optional;

public interface CategoryService {

    Category createCategory(String name, String description);

    Iterable<Category> findAllCategories();

    Optional<Category> findById(Integer id);

    void deleteCategory(Integer id);
}

