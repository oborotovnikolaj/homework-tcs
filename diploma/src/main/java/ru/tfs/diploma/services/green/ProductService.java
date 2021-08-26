package ru.tfs.diploma.services.green;

import ru.tfs.diploma.dto.green.ProductDto;

import java.util.List;

/**
 * Сервис для администрирования клиентов
 */
public interface ProductService {

    List<ProductDto> findAll(Long currencyId, Long languageId);

    ProductDto findById(Long id, Long currencyId, Long languageId);

    ProductDto save(ProductDto productDto);

    void delete(Long productId);

}
