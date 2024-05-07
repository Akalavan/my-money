package ru.akalavan.stocks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.akalavan.stocks.entity.Stock;

public interface StocksRepository extends CrudRepository<Stock, Integer> {

    Iterable<Stock> findByShortNameLikeIgnoreCase(@Param("short_name") String shortName);
}
