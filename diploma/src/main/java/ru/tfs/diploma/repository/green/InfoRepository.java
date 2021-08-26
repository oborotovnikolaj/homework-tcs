package ru.tfs.diploma.repository.green;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.green.Info;
import ru.tfs.diploma.entities.green.Product;

import java.util.List;

@Repository
public interface InfoRepository extends CrudRepository<Info, Long> {
    List<Info> findByProduct(Product product);

    //Можно было через фильтры
    //    https://stackoverflow.com/questions/49752429/using-a-hibernate-filter-with-spring-boot-jpa
    //    https://stackoverflow.com/questions/15844220/hibernate-multiple-filters-on-an-entity
    List<Info> findByProductIdAndLanguageId(Long productId, long languageID);
}
