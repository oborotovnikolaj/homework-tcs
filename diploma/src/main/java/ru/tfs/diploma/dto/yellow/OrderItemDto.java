package ru.tfs.diploma.dto.yellow;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.dto.green.ProductDto;

@Getter
@Setter
public class OrderItemDto {

    private Long id;
    private int version;

    private Integer quantity;

    private ProductDto product;

}
