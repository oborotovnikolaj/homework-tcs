package ru.tfs.gateway.dto.green;

import ru.tfs.gateway.dto.red.CurrencyDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductDto {

    private Long id;

    private Set<InfoDto> infos;
    private Set<ParameterDto> parameters;
    private BigDecimal price;
    private CurrencyDto currency;

}
