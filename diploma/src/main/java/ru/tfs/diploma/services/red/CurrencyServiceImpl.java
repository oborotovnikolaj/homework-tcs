package ru.tfs.diploma.services.red;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.diploma.dto.red.CurrencyDto;
import ru.tfs.diploma.entities.red.Currency;
import ru.tfs.diploma.repository.red.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("currencyService")
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<CurrencyDto> findAll() {
        List<Currency> currencies = new ArrayList<>();
        currencyRepository.findAll().forEach(currencies::add);
        return currencies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CurrencyDto findById(Long id) {
        return convertToDto(currencyRepository.findById(id).orElse(null));
    }

    @Override
    public CurrencyDto save(CurrencyDto currency) {
        Currency save = currencyRepository.save(modelMapper.map(currency, Currency.class));
        return modelMapper.map(save, CurrencyDto.class);
    }

    @Override
    public void delete(CurrencyDto currency) {
        currencyRepository.delete(modelMapper.map(currency, Currency.class));
    }

    private CurrencyDto convertToDto(Currency currency) {
        return modelMapper.map(currency, CurrencyDto.class);
    }
}
