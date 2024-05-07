package ru.akalavan.budget.controller.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.akalavan.budget.entity.Category;
import ru.akalavan.budget.entity.TypeOperation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NewMonetaryTransactionPayload(
        @NotNull
        @Size(min = 2, max = 255)
        String name,

        @Size(max = 255)
        String description,

        @NotNull
        @Digits(integer = 12, fraction = 2)
        BigDecimal amount,

        @NotNull
        Integer categoryId,

        @NotNull
        Integer typeOperationId,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime dateOperation
) {
}
