package ru.akalavan.stocks.controller.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record NewStockPayload(
        @NotNull
        @Size(min = 3, max = 100)
        String name,

        @NotNull
        @Size(min = 1, max = 20)
        @Column(name = "short_name")
        String shortName,

        @NotNull
        @Digits(integer = 12, fraction = 2)
        BigDecimal amount) {

}
