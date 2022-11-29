package nl.dw.currency.repository;

import nl.dw.currency.model.Bank;
import nl.dw.currency.model.Currency;
import nl.dw.currency.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IExchangeRepository extends JpaRepository<Exchange, Long> {
	Optional<Exchange> findExchangeByBankAndCurrencyAndUrlSource (Bank bank, Currency currency, String urlSource);
}
