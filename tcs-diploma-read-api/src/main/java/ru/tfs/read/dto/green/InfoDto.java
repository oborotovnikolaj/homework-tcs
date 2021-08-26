package ru.tfs.read.dto.green;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoDto {

    private Long id;

    private String tittle;
    private String description;

    private Long productId;
    private Long languageId;

}
