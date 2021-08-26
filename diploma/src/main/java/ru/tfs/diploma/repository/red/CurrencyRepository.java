package ru.tfs.diploma.repository.red;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.red.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
