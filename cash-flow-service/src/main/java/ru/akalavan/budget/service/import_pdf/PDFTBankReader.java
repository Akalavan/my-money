package ru.akalavan.budget.service.import_pdf;

import org.springframework.stereotype.Component;
import ru.akalavan.budget.entity.MonetaryTransaction;
import ru.akalavan.budget.service.TypeOperationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class PDFTBankReader extends PDFReader {

    private final TypeOperationService typeOperationService;

    private final int LENGTH_MAIN_RECORD = 6;

    public PDFTBankReader(TypeOperationService typeOperationService) {
        this.typeOperationService = typeOperationService;
    }

    @Override
    void readMonetaryTransaction(String text, int page, List<MonetaryTransaction> monetaryTransactionList) {
        int startLine = 2;
        if (page == 1) {
            startLine = 14;
        }

        MonetaryTransaction monetaryTransaction = new MonetaryTransaction();

        LocalDate localDate = LocalDate.now();
        String[] allLines = text.split("\r+\n+");
        String[] requiredLines = Arrays.copyOfRange(allLines, startLine, allLines.length - 3);

        for (String line : requiredLines) {
            String[] columns = line.split("\\|");
            String[] date = columns[0].split("\\.");

            if (columns.length == LENGTH_MAIN_RECORD) {
                if (monetaryTransaction.getDateOperation() != null) {
                    monetaryTransactionList.add(monetaryTransaction);
                }

                monetaryTransaction = new MonetaryTransaction();
                monetaryTransaction.setName("Unknown");

                setAmount(columns, monetaryTransaction);
                setTypeOperation(monetaryTransaction);

                monetaryTransaction.setCategory(null);
                monetaryTransaction.setDescription(columns[4]);

                localDate = LocalDate.of(Integer.parseInt(date[2]), Month.of(Integer.parseInt(date[1])), Integer.parseInt(date[0]));
            } else {
                if (columns.length == 1) {
                    monetaryTransaction.setDescription(monetaryTransaction.getDescription() + " " + columns[0]);
                } else if (columns.length == 3) {
                    monetaryTransaction.setDescription(monetaryTransaction.getDescription() + " " + columns[2]);
                    setDateOperation(columns[0], localDate, monetaryTransaction);
                } else {
                    setDateOperation(columns[0], localDate, monetaryTransaction);
                }
            }
        }
        monetaryTransactionList.add(monetaryTransaction);
    }

    private void setTypeOperation(MonetaryTransaction monetaryTransaction) {
        int sign = monetaryTransaction.getAmount().signum();
        monetaryTransaction.setTypeOperation(sign == -1 ?
                typeOperationService.findById(2)
                        .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.type_operations.not_found")) :
                typeOperationService.findById(1)
                        .orElseThrow(() -> new NoSuchElementException("cash_flow.errors.type_operations.not_found")));
    }

    private void setAmount(String[] columns, MonetaryTransaction monetaryTransaction) {
        double amount = Double.parseDouble(columns[2].substring(1, columns[2].length() - 1).replaceAll(" ", ""));
        monetaryTransaction.setAmount(BigDecimal.valueOf(amount));
    }

    private void setDateOperation(String timeString, LocalDate localDate, MonetaryTransaction monetaryTransaction) {
        String[] time = timeString.split(":");
        LocalTime localTime = LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        monetaryTransaction.setDateOperation(LocalDateTime.of(localDate, localTime));
    }
}
