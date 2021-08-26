package ru.tfs.gateway.dto.green;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoDto {

    private Long id;

    private String tittle;
    private String description;

    private Long productId;
    private Long languageId;

}
