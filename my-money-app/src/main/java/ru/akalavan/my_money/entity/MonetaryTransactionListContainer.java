package ru.akalavan.my_money.entity;

import ru.akalavan.my_money.dto.MonetaryTransactionDTO;

import java.util.ArrayList;
import java.util.List;
public class MonetaryTransactionListContainer {

    private List<MonetaryTransactionDTO> monetaryTransactions;

    public MonetaryTransactionListContainer(List<MonetaryTransactionDTO> monetaryTransactions) {
        this.monetaryTransactions = monetaryTransactions;
    }

    public void addMonetaryTransaction(MonetaryTransactionDTO monetaryTransaction) {
        monetaryTransactions.add(monetaryTransaction);
    }

    public List<MonetaryTransactionDTO> getMonetaryTransactions() {
        if (monetaryTransactions == null) {
            monetaryTransactions = new ArrayList<>();
        }
        return monetaryTransactions;
    }
}
