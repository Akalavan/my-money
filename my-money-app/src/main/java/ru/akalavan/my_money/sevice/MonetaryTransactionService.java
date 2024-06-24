package ru.akalavan.my_money.sevice;

import ru.akalavan.my_money.controller.payload.NewMonetaryTransactionPayload;
import ru.akalavan.my_money.dto.MonetaryTransactionDTO;
import ru.akalavan.my_money.entity.Category;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface MonetaryTransactionService {

    Stream<NewMonetaryTransactionPayload> convertMonetaryTransactionDroToMonetaryTransactionPayload(List<MonetaryTransactionDTO> monetaryTransactionDTO);

    void saveMonetaryTransaction(Stream<NewMonetaryTransactionPayload> payloadStream);

    Map<Category, Double> getStatisticForMouth(int month);
}
