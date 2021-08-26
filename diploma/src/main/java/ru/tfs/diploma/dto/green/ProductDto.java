package ru.tfs.diploma.dto.green;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.dto.red.CurrencyDto;

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
