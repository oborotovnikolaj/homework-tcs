package ru.tfs.diploma.controllers.green;

import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tfs.diploma.dto.green.ProductDto;
import ru.tfs.diploma.services.green.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> findAll(@RequestParam long currencyId, @RequestParam long languageId) {
        return productService.findAll(currencyId, languageId);
    }

    @GetMapping(value = "/read/", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<ProductDto> findAll() {
        return productService.findAll(null, null);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id, @RequestParam Long currencyId, @RequestParam Long languageId) {
        return productService.findById(id, currencyId, languageId);
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

}
