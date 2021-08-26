package ru.tfs.diploma.controllers.red;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tfs.diploma.dto.red.LanguageDto;
import ru.tfs.diploma.services.red.LanguageService;

import java.util.List;

@RestController
@RequestMapping("/language")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public List<LanguageDto> findAll() {
        return languageService.findAll();
    }

    @GetMapping("/{id}")
    public LanguageDto findById(@PathVariable Long id) {
        return languageService.findById(id);
    }

    @PostMapping
    public LanguageDto save(@RequestBody LanguageDto language) {
        return languageService.save(language);
    }

    @DeleteMapping
    public void delete(@RequestBody LanguageDto language) {
        languageService.delete(language);
    }
}
