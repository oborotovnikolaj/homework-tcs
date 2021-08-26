package ru.tfs.diploma.repository.yellow;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.yellow.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
