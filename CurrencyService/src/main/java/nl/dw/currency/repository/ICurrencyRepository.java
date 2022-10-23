package nl.dw.currency.repository;

import nl.dw.currency.model.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICurrencyRepository extends CrudRepository<Currency, Long> {
    boolean existsCurrenciesBySymbol(String symbolCurrency);
    Optional<Currency> findCurrencyBySymbol (String symbol);
}
