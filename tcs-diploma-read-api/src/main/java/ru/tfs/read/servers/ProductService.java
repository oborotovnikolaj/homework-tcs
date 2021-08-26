package ru.tfs.read.servers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.tfs.read.client.ProductClient;
import ru.tfs.read.dto.green.ProductDto;
import ru.tfs.read.repo.ProductRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductClient productClient;

    public Optional<ProductDto> getProduct(Long id, Long languageId) {
        log.info("get customer with id {}", id);
        return productRepo.find(id, languageId);
    }

    @Scheduled(fixedDelayString = "20000")
    public void updateProductRepo() {
        List<ProductDto> all = productClient.findAll();
        all.forEach(productRepo::put);
    }

}
