package ru.tfs.gateway.dto.red;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyDto {

    private Long id;
    private String name;
    private BigDecimal multiplier;
}
