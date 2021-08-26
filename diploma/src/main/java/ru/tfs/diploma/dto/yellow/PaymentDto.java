package ru.tfs.diploma.dto.yellow;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDto {

    private Long id;
    private int version;

    private Long orderId;
    private Long clientId;
    private Long currencyId;
    private BigDecimal value;
    private String cardPan;
}
