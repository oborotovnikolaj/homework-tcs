package ru.tfs.diploma;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.tfs.diploma.entities.red.Language;
import ru.tfs.diploma.repository.green.InfoRepository;
import ru.tfs.diploma.repository.green.ParameterRepository;
import ru.tfs.diploma.repository.green.ProductRepository;
import ru.tfs.diploma.repository.grey.ClientRepository;
import ru.tfs.diploma.repository.red.CurrencyRepository;
import ru.tfs.diploma.repository.red.LanguageRepository;
import ru.tfs.diploma.repository.red.ParameterTypeRepository;
import ru.tfs.diploma.repository.yellow.OrderItemRepository;
import ru.tfs.diploma.repository.yellow.OrderRepository;
import ru.tfs.diploma.repository.yellow.PaymentRepository;
import ru.tfs.diploma.repository.yellow.ShipmentRepository;
import ru.tfs.diploma.services.green.ProductService;
import ru.tfs.diploma.services.grey.ClientService;
import ru.tfs.diploma.services.red.CurrencyService;
import ru.tfs.diploma.services.red.LanguageService;
import ru.tfs.diploma.services.red.ParameterTypeService;
import ru.tfs.diploma.services.yellow.OrderService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@SpringJUnitConfig(Application.class)
class DummyTest {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    LanguageService languageService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    ParameterTypeService parameterTypeService;

    @Autowired
    ProductService productService;

    @Autowired
    ClientService clientService;

    @Autowired
    OrderService orderService;

//    @Autowired
//    ShoppingBasketService shoppingBasketService;


    @Autowired
    InfoRepository infoRepository;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ParameterTypeRepository parameterTypeRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ShipmentRepository shipmentRepository;
    @Autowired
    PaymentRepository paymentRepository;


    @Test
    public void test() throws IOException {

        Language language = new Language();
        language.setId(1L);
        language.setName("Russian");

        objectMapper.writeValue(new File("src/test/resources/json/language.json"), language);

    }

    @Test
    @Transactional
    public void fullTest() throws IOException {
//
//        Language language = new Language();
//        language.setName("Russian");
////        language.setId(1L);
//        language = languageService.save(language);
//
//        Currency currency = new Currency();
////        currency.setId(2L);
//        currency.setName("rubles");
//        currency.setMultiplier(BigDecimal.valueOf(2));
//        currency = currencyService.save(currency);
//
//        ParameterType parameterType = new ParameterType();
////        parameterType.setId(3L);
//        parameterType.setName("weight");
//        parameterType = parameterTypeService.save(parameterType);
//
//        Client ru.tfs.diploma.client = new Client();
////        ru.tfs.diploma.client.setId(4L);
//        ru.tfs.diploma.client.setPhone("+7 999 999 99 99");
//        ru.tfs.diploma.client.setRegion("deep deep");
//        ru.tfs.diploma.client = clientService.save(ru.tfs.diploma.client);
//
//        Product product = new Product();
////        product.setId(5L);
//        product.setPrice(new BigDecimal("100.2"));
//
//        Info info = new Info();
////        info.setId(6L);
//        info.setDescription("boring");
//        info.setLanguage(language);
//        info.setTittle("table");
//        info.setProduct(product);
//
//        Parameter parameter = new Parameter();
////        parameter.setId(7L);
//        parameter.setParameterType(parameterType);
//        parameter.setProduct(product);
//        parameter.setValue("1000 tons");
//
//        InfoDto infoDto = modelMapper.map(info, InfoDto.class);
//        ParameterDto parameterDto = modelMapper.map(parameter, ParameterDto.class);
//        ProductDto productDto = modelMapper.map(product, ProductDto.class);
//        productDto.setInfos(Collections.singleton(infoDto));
//        productDto.setParameters(Collections.singleton(parameterDto));
//
//        productDto.setCurrencyId(currency.getId());
//
//        ProductDto productDto1 = productService.save(productDto);
//        ProductDto productDto2 = productService.findById(productDto1.getId(), currency.getId());
//
//        product = modelMapper.map(productDto2, Product.class);
//
//        Order order = new Order();
////        order.setId(8L);
//        order.setClient(ru.tfs.diploma.client);
//        order.setCurrency(currency);
//
//        OrderItem orderItem = new OrderItem();
////        orderItem.setId(9L);
//        orderItem.setOrder(order);
//        orderItem.setQuantity(10);
//        orderItem.setProduct(product);
//
//        Shipment shipment = new Shipment();
////        shipment.setId(11L);
//        shipment.setOrder(order);
//        shipment.setAddress("Moscow, Kremlin");
//
//        Payment payment = new Payment();
////        payment.setId(12L);
//        payment.setCurrency(currency);
//        payment.setOrder(order);
//        payment.setValue(new BigDecimal(100));
//        payment.setCardPan("cardPan");
//
//        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
//        OrderItemDto orderItemDto = modelMapper.map(orderItem, OrderItemDto.class);
//        ShipmentDto shipmentDto = modelMapper.map(shipment, ShipmentDto.class);
//        PaymentDto paymentDto = modelMapper.map(payment, PaymentDto.class);
//
////        Order order3 = shoppingBasketService.addOrder(ru.tfs.diploma.client.getId(), order.getId());
////        shoppingBasketService.createPayment(ru.tfs.diploma.client.getId(), order.getId());
//
//        orderDto.setOrderItems(Collections.singleton(orderItemDto));
//        orderDto.setShipmentDto(shipmentDto);
//        orderDto.setPaymentDto(paymentDto);
//
//        orderDto.setCurrencyId(currency.getId());
//
//        OrderDto orderDto1 = orderService.save(orderDto);
//        OrderDto orderDto2 = orderService.findById(orderDto1.getId(), orderDto1.getCurrencyId());
//
//        System.out.println("Все");
//
//
//        objectMapper.writeValue(new File("src/test/resources/json/entity/language.json"), languageRepository.findById(language.getId()).orElse(null));
//        objectMapper.writeValue(new File("src/test/resources/json/entity/parameterType.json"), parameterTypeRepository.findById(parameterType.getId()).orElse(null));
//        objectMapper.writeValue(new File("src/test/resources/json/entity/currency.json"), currencyRepository.findById(currency.getId()).orElse(null));
//
//        objectMapper.writeValue(new File("src/test/resources/json/entity/info.json"), infoRepository.findById(1L).orElse(null));
//        objectMapper.writeValue(new File("src/test/resources/json/entity/parameter.json"), parameterRepository.findById(1L).orElse(null));
//        objectMapper.writeValue(new File("src/test/resources/json/entity/product.json"), productRepository.findById(1L).orElse(null));
//
//        objectMapper.writeValue(new File("src/test/resources/json/entity/ru.tfs.diploma.client.json"), clientRepository.findById(ru.tfs.diploma.client.getId()).orElse(null));
//
//        objectMapper.writeValue(new File("src/test/resources/json/entity/order.json"), orderRepository.findById(1l).orElse(null));
//        List<OrderItemDto> orderItemDtos = new ArrayList<>(orderDto2.getOrderItems());
//        objectMapper.writeValue(new File("src/test/resources/json/entity/orderItem.json"),orderItemRepository.findById(1l).orElse(null));
//        objectMapper.writeValue(new File("src/test/resources/json/entity/shipment.json"), shipmentRepository.findById(1l).orElse(null));
//        objectMapper.writeValue(new File("src/test/resources/json/entity/payment.json"), paymentRepository.findById(1l).orElse(null));
//
//
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/language.json"), language);
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/parameterType.json"), parameterType);
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/currency.json"), currency);
//
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/info.json"), Iterables.getElement(productDto2.getInfos(), 0));
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/parameter.json"), Iterables.getElement(productDto2.getParameters(), 0));
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/product.json"), productDto2);
//
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/ru.tfs.diploma.client.json"), ru.tfs.diploma.client);
//
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/order.json"), orderDto2);
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/orderItem.json"), Iterables.getElement(orderDto2.getOrderItems(), 0));
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/shipment.json"), orderDto2.getShipmentDto());
//        objectMapper.writeValue(new File("src/test/resources/json/ru.tfs.diploma.ru.tfs.gateway.dto/payment.json"), orderDto2.getPaymentDto());
//
//
//        System.out.println("Все");

//        InfoDto infoDto = new InfoDto();
//        infoDto.setDescription(info.getDescription());
//        infoDto.setTittle(info.getTittle());
//
//        ProductDto productDto = new ProductDto();
//        productDto.setId(1L);
//        productDto.setPrice(product.getPrice());
//        productDto.setInfos(i);


//        productService.save(product);

    }

}