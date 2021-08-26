package ru.tfs.diploma.repository.green;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.diploma.entities.green.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

//    List<Info> findByIdAndLanguageId(Long productId, long languageID);

}
