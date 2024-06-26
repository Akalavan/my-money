package ru.akalavan.budget.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.akalavan.budget.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
