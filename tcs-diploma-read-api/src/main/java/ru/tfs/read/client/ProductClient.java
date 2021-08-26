package ru.tfs.read.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tfs.read.dto.green.ProductDto;

import java.util.List;

//@FeignClient(name = "product", url = "http://localhost:8080/product?{currencyId}&{languageId}")
@FeignClient(name = "product", url = "http://localhost:8080/product")
public interface ProductClient {

    @GetMapping("/read/")
//    List<ProductDto> findAll(@RequestParam(name = "currencyId") Long currencyId, @RequestParam(name = "languageId") Long languageId);
    List<ProductDto> findAll();

}
