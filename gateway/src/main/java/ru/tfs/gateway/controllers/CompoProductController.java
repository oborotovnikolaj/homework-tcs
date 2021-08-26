package ru.tfs.gateway.controllers;

import org.springframework.web.bind.annotation.*;
import ru.tfs.gateway.dto.green.ProductDto;
import ru.tfs.gateway.dto.red.CurrencyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.RoundingMode;

@RestController
@RequestMapping("/product")
public class CompoProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompoProductController.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{id}")
    public Mono<ProductDto> findById(@PathVariable Long id, @RequestParam Long currencyId, @RequestParam Long languageId) {
        LOGGER.info("findAll: {}", currencyId);

        Mono<ProductDto> productsDto = webClientBuilder.build()
                .get()
                .uri("http://localhost:8005/api/product/{id}?languageId={languageId}", id, languageId)
                .retrieve()
                .bodyToMono(ProductDto.class);

        Mono<CurrencyDto> currencyDto = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/currency/{currencyId}", currencyId)
                .retrieve()
                .bodyToMono(CurrencyDto.class);

//        return productsDto.map(productDto -> currencyDto.map(m -> convertCurrency(productDto, m)));
        return currencyDto.zipWith(productsDto, (cur, prod) -> convertCurrency(prod, cur));
    }

//    @GetMapping("/{id}")
//    public Flux<ProductDto> findById(@PathVariable Long id, @RequestParam Long currencyId, @RequestParam Long languageId) {
//        LOGGER.info("findAll: {}", currencyId);
//
//        Flux<ProductDto> productsDto = webClientBuilder.build()
//                .get()
//                .uri("http://localhost:8005/api/product/{id}?languageId={languageId}", id, languageId)
//                .retrieve()
//                .bodyToFlux(ProductDto.class);
//
//        Mono<CurrencyDto> currencyDto = webClientBuilder.build()
//                .get()
//                .uri("http://localhost:8080/currency/")
//                .retrieve()
//                .bodyToMono(CurrencyDto.class);
//
//        return productsDto.flatMap(productDto -> currencyDto.map(m -> convertCurrency(productDto, m)));
//    }

    private ProductDto convertCurrency(ProductDto productDto, CurrencyDto currencyDto) {
        productDto.setCurrency(currencyDto);
        productDto.setPrice(productDto.getPrice().divide(currencyDto.getMultiplier(), RoundingMode.HALF_UP));
        return productDto;
    }


}
