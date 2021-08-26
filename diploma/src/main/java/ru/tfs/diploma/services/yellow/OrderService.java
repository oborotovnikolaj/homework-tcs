package ru.tfs.diploma.services.yellow;

import ru.tfs.diploma.dto.yellow.OrderDto;

public interface OrderService {

    OrderDto findById(Long id, Long currencyId, Long languageId);

    OrderDto save(OrderDto orderDto, Long currencyId);

    void delete(Long orderId);
}
