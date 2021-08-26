package ru.tfs.diploma.dto.yellow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentDto {

    private Long id;
    private int version;

    private String address;
}
