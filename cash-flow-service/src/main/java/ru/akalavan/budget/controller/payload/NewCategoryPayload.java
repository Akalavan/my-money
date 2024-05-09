package ru.akalavan.budget.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewCategoryPayload(
        @NotNull(message = "{cash_flow.category.create.errors.name_is_null}")
        @Size(min = 2, max = 45, message = "{cash_flow.category.create.errors.name_size_is_invalid}")
        String name,

        @Size(max = 255, message = "{cash_flow.category.create.errors.description_size_is_invalid}")
        String description) {
}
