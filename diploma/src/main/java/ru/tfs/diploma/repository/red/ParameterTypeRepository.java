package ru.tfs.diploma.repository.red;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.red.ParameterType;

@Repository
public interface ParameterTypeRepository extends CrudRepository<ParameterType, Long> {
}
