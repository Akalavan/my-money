package ru.akalavan.my_money.controller.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NewMonetaryTransactionPayload(
        String name,
        String description,
        BigDecimal amount,
        Integer categoryId,
        Integer typeOperationId,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime dateOperation
) {
}
