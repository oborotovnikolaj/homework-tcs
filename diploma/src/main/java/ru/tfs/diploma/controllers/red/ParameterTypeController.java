package ru.tfs.diploma.controllers.red;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tfs.diploma.dto.red.ParameterTypeDto;
import ru.tfs.diploma.services.red.ParameterTypeService;

import java.util.List;

@RestController
@RequestMapping("/parametertype")
public class ParameterTypeController {

    private final ParameterTypeService parameterTypeService;

    @Autowired
    public ParameterTypeController(ParameterTypeService parameterTypeService) {
        this.parameterTypeService = parameterTypeService;
    }

    @GetMapping
    public List<ParameterTypeDto> findAll() {
        return parameterTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ParameterTypeDto findById(@PathVariable Long id) {
        return parameterTypeService.findById(id);
    }

    @PostMapping
    public ParameterTypeDto save(@RequestBody ParameterTypeDto parameterType) {
        return parameterTypeService.save(parameterType);
    }

    @DeleteMapping
    public void delete(@RequestBody ParameterTypeDto parameterType) {
        parameterTypeService.delete(parameterType);
    }
}
