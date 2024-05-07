package ru.akalavan.stocks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.akalavan.stocks.controller.payload.NewStockPayload;
import ru.akalavan.stocks.entity.Stock;
import ru.akalavan.stocks.service.StockService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("assets-api/stocks")
public class StocksRestController {

    private final StockService stockService;

    @GetMapping
    public Iterable<Stock> findStocks(@RequestParam(name = "shortNameFilter", required = false) String shortNameFilter) {
        return stockService.findAllStocks(shortNameFilter);
    }

    @PostMapping
    public ResponseEntity<?> createStock(@Valid @RequestBody NewStockPayload payload,
                                         BindingResult bindingResult,
                                         UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Stock stock = stockService.createProduct(payload.name(), payload.shortName(), payload.amount());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("assets-api/stocks/{stockId}")
                            .build(Map.of("stockId", stock.getId())))
                    .body(stock);
        }
    }
}
