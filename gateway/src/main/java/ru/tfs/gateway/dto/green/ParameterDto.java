package ru.tfs.gateway.dto.green;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterDto {

    private Long id;

    private String value;
    private Long parameterTypeId;
    private Long productId;

}
