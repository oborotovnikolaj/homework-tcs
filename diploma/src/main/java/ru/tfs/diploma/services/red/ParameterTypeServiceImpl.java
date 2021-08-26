package ru.tfs.diploma.services.red;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.diploma.dto.red.ParameterTypeDto;
import ru.tfs.diploma.entities.red.ParameterType;
import ru.tfs.diploma.repository.red.ParameterTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("parameterTypeService")
@Transactional
public class ParameterTypeServiceImpl implements ParameterTypeService {

    private final ParameterTypeRepository parameterTypeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ParameterTypeServiceImpl(ParameterTypeRepository parameterTypeRepository) {
        this.parameterTypeRepository = parameterTypeRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<ParameterTypeDto> findAll() {
        List<ParameterType> parameterTypes = new ArrayList<>();
        parameterTypeRepository.findAll().forEach(parameterTypes::add);
        return parameterTypes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ParameterTypeDto findById(Long id) {
        return convertToDto(parameterTypeRepository.findById(id).orElse(null));
    }

    @Override
    public ParameterTypeDto save(ParameterTypeDto parameterType) {
        ParameterType save = parameterTypeRepository.save(convertToEntity(parameterType));
        return convertToDto(save);
    }

    @Override
    public void delete(ParameterTypeDto parameterType) {
        parameterTypeRepository.delete(convertToEntity(parameterType));
    }

    private ParameterTypeDto convertToDto(ParameterType parameterType) {
        return modelMapper.map(parameterType, ParameterTypeDto.class);
    }

    private ParameterType convertToEntity(ParameterTypeDto parameterTypeDto) {
        return modelMapper.map(parameterTypeDto, ParameterType.class);
    }
}
