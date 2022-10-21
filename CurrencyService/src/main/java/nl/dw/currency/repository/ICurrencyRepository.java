package nl.dw.currency.repository;

import nl.dw.currency.model.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyRepository extends CrudRepository<Currency, Long> {
    boolean existsCurrenciesBySymbol(String symbolCurrency);
}
