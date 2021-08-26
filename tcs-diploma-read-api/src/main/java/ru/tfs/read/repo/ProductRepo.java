package ru.tfs.read.repo;

import org.springframework.stereotype.Component;
import ru.tfs.read.dto.green.InfoDto;
import ru.tfs.read.dto.green.ProductDto;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProductRepo {
    private final Map<String, ProductDto> products;

    public ProductRepo() {
        this.products = new ConcurrentHashMap<>();
    }

    public Optional<ProductDto> find(Long id, Long languageId) {
        return Optional.ofNullable(products.get(String.join(":", id.toString(), languageId.toString())));
    }

    public void put(ProductDto productDto) {

        Set<InfoDto> infoDtos = new HashSet<>(productDto.getInfos());

        for (InfoDto infoDto : infoDtos) {
            productDto.setInfos(Collections.singleton(infoDto));
            products.put(
                    String.join(":", productDto.getId().toString(), infoDto.getLanguageId().toString()),
                    new ProductDto (productDto)
            );
        }
    }
}
