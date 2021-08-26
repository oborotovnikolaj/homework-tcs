package ru.tfs.read.dto.green;

import lombok.*;
import ru.tfs.read.dto.red.CurrencyDto;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private Set<InfoDto> infos;
    private Set<ParameterDto> parameters;
    private BigDecimal price;
    private CurrencyDto currency;

    public ProductDto(ProductDto productDto) {
        this.id = productDto.id;
        this.infos = productDto.infos;
        this.parameters = productDto.parameters;
        this.price = productDto.price;
        this.currency = productDto.currency;
    }

}
