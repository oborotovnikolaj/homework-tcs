package ru.tfs.diploma.repository.green;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.green.Parameter;
import ru.tfs.diploma.entities.green.Product;

import java.util.List;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, Long> {
    List<Parameter> findByProduct(Product product);
}
