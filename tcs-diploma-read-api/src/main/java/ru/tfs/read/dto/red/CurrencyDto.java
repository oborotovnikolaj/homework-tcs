package ru.tfs.read.dto.red;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

    private Long id;
    private String name;
    private BigDecimal multiplier;
}
