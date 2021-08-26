package ru.tfs.diploma.repository.yellow;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.yellow.Order;
import ru.tfs.diploma.entities.yellow.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
