package ru.akalavan.my_money.client;

import ru.akalavan.my_money.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoriesRestClient {

    List<Category> findAllCategory();

    Optional<Category> findById(Integer id);

    Category createCategory(String name, String description) throws BadRequestException;

    void updateCategory(Integer id, String name, String description);
}
