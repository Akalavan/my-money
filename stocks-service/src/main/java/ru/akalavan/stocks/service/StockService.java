package ru.akalavan.stocks.service;

import ru.akalavan.stocks.entity.Stock;

import java.math.BigDecimal;
import java.util.Optional;

public interface StockService {

    Iterable<Stock> findAllStocks(String shortNameFilter);

    Stock createProduct(String name, String shortName, BigDecimal amount);

    Optional<Stock> findStock(int stockId);
}
