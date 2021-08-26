package ru.tfs.read.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.read.dto.green.ProductDto;
import ru.tfs.read.servers.ProductService;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ReadApiController {

    private final ProductService productService;

    @GetMapping(value = "/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id, @RequestParam long languageId) {
        Optional<ProductDto> optionalProduct = productService.getProduct(id, languageId);
        return optionalProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
