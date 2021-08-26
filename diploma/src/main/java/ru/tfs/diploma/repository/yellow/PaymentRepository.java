package ru.tfs.diploma.repository.yellow;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.yellow.Order;
import ru.tfs.diploma.entities.yellow.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    Payment findByOrder(Order order);
}
