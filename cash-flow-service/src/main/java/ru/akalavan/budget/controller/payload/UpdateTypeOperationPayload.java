package ru.akalavan.budget.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTypeOperationPayload(
        @NotNull
        @Size(min = 2, max = 45)
        String name,

        @Size(max = 255)
        String description) {
}
