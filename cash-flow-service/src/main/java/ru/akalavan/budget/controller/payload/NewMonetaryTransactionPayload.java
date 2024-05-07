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
        @NotNull(message = "{cash_flow.monetary_transactions.create.errors.name_is_null}")
        @Size(min = 2, max = 255, message = "{cash_flow.monetary_transactions.create.errors.name_size_is_invalid}")
        String name,

        @Size(max = 255, message = "{cash_flow.monetary_transactions.create.errors.description_size_is_invalid}")
        String description,

        @NotNull(message = "{cash_flow.monetary_transactions.create.errors.amount_is_null}")
        @Digits(integer = 12, fraction = 2)
        BigDecimal amount,

        @NotNull(message = "{cash_flow.monetary_transactions.create.errors.category_id_is_null}")
        Integer categoryId,

        @NotNull(message = "{cash_flow.monetary_transactions.create.errors.type_operation_id_is_null}")
        Integer typeOperationId,

        @NotNull(message = "{cash_flow.monetary_transactions.create.errors.date_operation_id_is_null}")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime dateOperation
) {
}
