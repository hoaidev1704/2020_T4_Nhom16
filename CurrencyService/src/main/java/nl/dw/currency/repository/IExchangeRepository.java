package nl.dw.currency.repository;

import nl.dw.currency.model.Exchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExchangeRepository extends CrudRepository<Exchange, Long> {}
