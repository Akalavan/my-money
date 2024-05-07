package ru.akalavan.stocks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akalavan.stocks.entity.Stock;
import ru.akalavan.stocks.repository.StocksRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultStockService implements StockService {

    private final StocksRepository stocksRepository;

    @Override
    public Iterable<Stock> findAllStocks(String shortNameFilter) {
        if (shortNameFilter != null && !shortNameFilter.isBlank()) {
            return stocksRepository.findByShortNameLikeIgnoreCase(shortNameFilter);
        } else {
            return stocksRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Stock createProduct(String name, String shortName, BigDecimal amount) {
        return stocksRepository.save(new Stock(null, name, shortName, amount));
    }

    @Override
    public Optional<Stock> findStock(int stockId) {
        return stocksRepository.findById(stockId);
    }
}
