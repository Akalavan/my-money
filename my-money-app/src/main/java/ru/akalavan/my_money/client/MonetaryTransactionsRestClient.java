package ru.akalavan.my_money.client;

import org.springframework.web.multipart.MultipartFile;
import ru.akalavan.my_money.entity.Category;
import ru.akalavan.my_money.entity.MonetaryTransaction;
import ru.akalavan.my_money.entity.TypeOperation;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MonetaryTransactionsRestClient {

    MonetaryTransaction createMonetaryTransaction(String name, String description, BigDecimal amount,
                                                  Category category, TypeOperation typeOperation, LocalDateTime dateOperation);

    List<MonetaryTransaction> findAllMonetaryTransaction();

    Optional<MonetaryTransaction> findById(Integer id);

    void updateMonetaryTransaction(Integer id, String name, String description, BigDecimal amount,
                                   Integer categoryId, Integer typeOperationId, LocalDateTime dateOperation);

    List<MonetaryTransaction> getMonetaryTransactionsFromPDF(MultipartFile file) throws IOException;
}
