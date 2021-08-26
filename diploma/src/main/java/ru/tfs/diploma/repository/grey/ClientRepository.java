package ru.tfs.diploma.repository.grey;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.grey.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
