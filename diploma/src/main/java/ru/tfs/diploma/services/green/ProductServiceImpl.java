package ru.tfs.diploma.services.green;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.diploma.dto.green.InfoDto;
import ru.tfs.diploma.dto.green.ParameterDto;
import ru.tfs.diploma.dto.green.ProductDto;
import ru.tfs.diploma.dto.red.CurrencyDto;
import ru.tfs.diploma.entities.green.Info;
import ru.tfs.diploma.entities.green.Parameter;
import ru.tfs.diploma.entities.green.Product;
import ru.tfs.diploma.entities.red.Currency;
import ru.tfs.diploma.entities.red.ParameterType;
import ru.tfs.diploma.repository.green.InfoRepository;
import ru.tfs.diploma.repository.green.ParameterRepository;
import ru.tfs.diploma.repository.green.ProductRepository;
import ru.tfs.diploma.services.red.CurrencyService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    private final InfoRepository infoRepository;
    private final ParameterRepository parameterRepository;
    private final ProductRepository productRepository;
    private final CurrencyService currencyService;

    private final ModelMapper modelMapper;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public ProductServiceImpl(
            CurrencyService currencyService,
            InfoRepository infoRepository,
            ParameterRepository parameterRepository,
            ProductRepository productRepository
    ) {
        this.currencyService = currencyService;
        this.infoRepository = infoRepository;
        this.parameterRepository = parameterRepository;
        this.productRepository = productRepository;

        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<ProductDto> findAll(Long currencyId, Long languageId) {
        List<ProductDto> products = new ArrayList<>();
        productRepository.findAll().forEach(product -> products.add(convertToDto(product, currencyId, languageId)));
        return products;
    }

    @Override
    public ProductDto findById(Long id, Long currencyId, Long languageId) {
        return convertToDto(productRepository.findById(id).orElse(null), currencyId, languageId);
    }

    @Override
    public ProductDto save(ProductDto productDto) {

        Product product = modelMapper.map(productDto, Product.class);

        Currency currency = modelMapper.map(productDto.getCurrency(), Currency.class);
        product.setPrice(product.getPrice().multiply(currency.getMultiplier()));

        List<Info> infos = new ArrayList<>();
        productDto.getInfos().forEach(infoDto -> infos.add(modelMapper.map(infoDto, Info.class)));
        infos.forEach(info -> info.setProduct(product));

        List<Parameter> parameters = new ArrayList<>();
        productDto.getParameters().forEach(parameterDto -> parameters.add(modelMapper.map(parameterDto, Parameter.class)));
        parameters.forEach(parameter -> parameter.setProduct(product));

        parameters.forEach(parameter ->
                parameter.setParameterType(em.getReference(ParameterType.class, parameter.getParameterType().getId())));

        productRepository.save(product);
        infoRepository.saveAll(infos);
        parameterRepository.saveAll(parameters);

        return convertToDto(product, currency.getId(), null);
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

// Замени  на  private ProductDto convertToDto(Product product, long currencyId, List<Long> languageIds) {
    private ProductDto convertToDto(Product product, Long currencyId, Long languageId) {

        List<Info> infos = languageId != null ?
                infoRepository.findByProductIdAndLanguageId(product.getId(), languageId) : infoRepository.findByProduct(product);

        Set<InfoDto> infosDto = new HashSet<>();
        infos.forEach(info -> infosDto.add(modelMapper.map(info, InfoDto.class)));

        List<Parameter> parameters = parameterRepository.findByProduct(product);
        Set<ParameterDto> parametersDto = new HashSet<>();
        parameters.forEach(parameter -> parametersDto.add(modelMapper.map(parameter, ParameterDto.class)));

        ProductDto productDto = modelMapper.map(product, ProductDto.class);

        if (currencyId != null) {
            CurrencyDto currencyDto = currencyService.findById(currencyId);
            productDto.setPrice(productDto.getPrice().divide(currencyDto.getMultiplier(), RoundingMode.HALF_UP));
            productDto.setCurrency(currencyDto);
        }

        productDto.setInfos(infosDto);
        productDto.setParameters(parametersDto);


        return productDto;
    }

}
