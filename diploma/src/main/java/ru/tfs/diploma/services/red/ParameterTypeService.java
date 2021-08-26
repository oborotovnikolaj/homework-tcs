package ru.tfs.diploma.services.red;

import ru.tfs.diploma.dto.red.ParameterTypeDto;

import java.util.List;

public interface ParameterTypeService {

    List<ParameterTypeDto> findAll();

    ParameterTypeDto findById(Long id);

    ParameterTypeDto save(ParameterTypeDto parameterType);

    void delete(ParameterTypeDto parameterType);

}
