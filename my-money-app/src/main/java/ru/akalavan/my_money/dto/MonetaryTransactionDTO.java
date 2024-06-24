package ru.akalavan.my_money.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonetaryTransactionDTO {
    private String name;
    private String description;
    private BigDecimal amount;
    private int categoryId;
    private int typeOperationId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateOperation;
}
