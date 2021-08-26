package ru.tfs.diploma.dto.yellow;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.dto.grey.ClientDto;
import ru.tfs.diploma.dto.red.CurrencyDto;

import java.util.Set;

@Getter
@Setter
public class OrderDto {

    private Long id;
    private int version;

    private ClientDto client;
    private CurrencyDto currency;

    private Set<OrderItemDto> orderItems;
    private ShipmentDto shipmentDto;
    private PaymentDto paymentDto;

}
