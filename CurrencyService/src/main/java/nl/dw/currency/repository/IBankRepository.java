package nl.dw.currency.repository;

import nl.dw.currency.model.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBankRepository extends CrudRepository<Bank, Long> {
	Optional<Bank> findBankByName(String bankName);
}
