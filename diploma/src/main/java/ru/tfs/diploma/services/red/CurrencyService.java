package ru.tfs.diploma.services.red;

import ru.tfs.diploma.dto.red.CurrencyDto;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDto> findAll();

    CurrencyDto findById(Long id);

    CurrencyDto save(CurrencyDto currency);

    void delete(CurrencyDto currency);

}
