package ru.akalavan.budget.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "budget", name = "monetary_transactions")
public class MonetaryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    @Digits(integer=12, fraction=2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "categories_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_operation_id", nullable = false)
    private TypeOperation typeOperation;

    @Column(name = "date_operation", columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateOperation;
}
