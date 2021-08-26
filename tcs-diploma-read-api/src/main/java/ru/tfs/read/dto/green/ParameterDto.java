package ru.tfs.read.dto.green;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDto {

    private Long id;

    private String value;
    private Long parameterTypeId;
    private Long productId;

}
