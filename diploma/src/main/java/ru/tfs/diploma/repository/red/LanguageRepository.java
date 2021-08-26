package ru.tfs.diploma.repository.red;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.red.Language;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Long> {
}
