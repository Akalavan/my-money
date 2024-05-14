package ru.akalavan.my_money.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MonetaryTransaction(
        int id,
        String name,
        String description,
        BigDecimal amount,
        Category category,
        TypeOperation typeOperation,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime dateOperation
) {
}
