package ru.akalavan.budget.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCategoryPayload(
        @NotNull(message = "{cash_flow.category.update.errors.name_is_null}")
        @Size(min = 3, max = 45, message = "{cash_flow.category.update.errors.name_size_is_invalid}")
        String name,

        @Size(max = 255, message = "{cash_flow.category.update.errors.description_size_is_invalid}")
        String description) {
}