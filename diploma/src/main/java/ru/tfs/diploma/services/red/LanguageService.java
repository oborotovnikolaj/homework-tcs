package ru.tfs.diploma.services.red;

import ru.tfs.diploma.dto.red.LanguageDto;

import java.util.List;

public interface LanguageService {

    List<LanguageDto> findAll();

    LanguageDto findById(Long id);

    LanguageDto save(LanguageDto language);

    void delete(LanguageDto language);

}
