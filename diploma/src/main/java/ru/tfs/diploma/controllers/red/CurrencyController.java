package ru.tfs.diploma.controllers.red;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tfs.diploma.dto.red.CurrencyDto;
import ru.tfs.diploma.services.red.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public List<CurrencyDto> findAll() {
        return currencyService.findAll();
    }


    @GetMapping(value = "/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public CurrencyDto findById(@PathVariable Long id) {
        return currencyService.findById(id);
    }

    @PostMapping
    public CurrencyDto save(@RequestBody CurrencyDto currency) {
        return currencyService.save(currency);
    }

    @DeleteMapping
    public void delete(@RequestBody CurrencyDto currencyDto) {
        currencyService.delete(currencyDto);
    }
}
