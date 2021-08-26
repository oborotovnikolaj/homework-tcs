package ru.tfs.diploma.repository.yellow;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.yellow.Order;
import ru.tfs.diploma.entities.yellow.Shipment;

@Repository
public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
    Shipment findByOrder(Order order);
}
