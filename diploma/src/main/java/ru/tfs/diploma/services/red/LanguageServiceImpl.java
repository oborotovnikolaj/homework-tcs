package ru.tfs.diploma.services.red;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.diploma.dto.red.LanguageDto;
import ru.tfs.diploma.entities.red.Language;
import ru.tfs.diploma.repository.red.LanguageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("languageService")
@Transactional
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<LanguageDto> findAll() {
        List<Language> languages = new ArrayList<>();
        languageRepository.findAll().forEach(languages::add);
        return languages.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public LanguageDto findById(Long id) {
        return convertToDto(languageRepository.findById(id).orElse(null));
    }

    @Override
    public LanguageDto save(LanguageDto language) {
        Language save = languageRepository.save(convertToEntity(language));
        return convertToDto(save);
    }

    @Override
    public void delete(LanguageDto language) {
        languageRepository.delete(convertToEntity(language));
    }

    private LanguageDto convertToDto(Language language) {
        return modelMapper.map(language, LanguageDto.class);
    }

    private Language convertToEntity(LanguageDto languageDto) {
        return modelMapper.map(languageDto, Language.class);
    }
}
