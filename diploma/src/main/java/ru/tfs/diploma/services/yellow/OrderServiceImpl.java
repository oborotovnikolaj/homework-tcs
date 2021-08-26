package ru.tfs.diploma.services.yellow;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.diploma.dto.red.CurrencyDto;
import ru.tfs.diploma.dto.yellow.OrderDto;
import ru.tfs.diploma.dto.yellow.OrderItemDto;
import ru.tfs.diploma.dto.yellow.PaymentDto;
import ru.tfs.diploma.dto.yellow.ShipmentDto;
import ru.tfs.diploma.entities.green.Product;
import ru.tfs.diploma.entities.grey.Client;
import ru.tfs.diploma.entities.yellow.Order;
import ru.tfs.diploma.entities.yellow.OrderItem;
import ru.tfs.diploma.entities.yellow.Payment;
import ru.tfs.diploma.entities.yellow.Shipment;
import ru.tfs.diploma.repository.yellow.OrderItemRepository;
import ru.tfs.diploma.repository.yellow.OrderRepository;
import ru.tfs.diploma.repository.yellow.PaymentRepository;
import ru.tfs.diploma.repository.yellow.ShipmentRepository;
import ru.tfs.diploma.services.green.ProductService;
import ru.tfs.diploma.services.red.CurrencyService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("order")
@Transactional
public class OrderServiceImpl implements OrderService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShipmentRepository shipmentRepository;

    private final CurrencyService currencyService;
    private final ProductService productService;

    private final ModelMapper modelMapper;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public OrderServiceImpl(
            CurrencyService currencyService,
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ShipmentRepository shipmentRepository,
            ProductService productService) {

        this.paymentRepository = paymentRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.shipmentRepository = shipmentRepository;

        this.currencyService = currencyService;
        this.productService = productService;

        this.modelMapper = new ModelMapper();
    }

    @Override
    public OrderDto findById(Long id, Long currencyId, Long languageId) {
        return convertToDto(orderRepository.findById(id).orElse(null), currencyId, languageId);
    }

    @Override
    public OrderDto save(OrderDto orderDto, Long currencyId) {

        Order order = modelMapper.map(orderDto, Order.class);
        //todo NPE
        order.setClient(em.getReference(Client.class, orderDto.getClient().getId()));

        List<OrderItem> orderItems = new ArrayList<>();
        orderDto.getOrderItems().forEach(orderItemDto -> orderItems.add(modelMapper.map(orderItemDto, OrderItem.class)));
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderItems.forEach(orderItem ->
                orderItem.setProduct(em.getReference(Product.class, orderItem.getProduct().getId())));

        Shipment shipment = modelMapper.map(orderDto.getShipmentDto(), Shipment.class);
        shipment.setOrder(order);

        Payment payment = modelMapper.map(orderDto.getPaymentDto(), Payment.class);
        payment.setValue(payment.getValue().multiply(orderDto.getCurrency().getMultiplier()));
        payment.setOrder(order);

        Order save = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        shipmentRepository.save(shipment);
        paymentRepository.save(payment);

        return convertToDto(save, currencyId, null);
    }

    @Override
    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderDto convertToDto(Order order, Long currencyId, Long languageId) {

        Shipment shipment = shipmentRepository.findByOrder(order);
        ShipmentDto shipmentDto = modelMapper.map(shipment, ShipmentDto.class);

        CurrencyDto currencyDto = currencyService.findById(currencyId);

        Payment payment = paymentRepository.findByOrder(order);
        PaymentDto paymentDto = modelMapper.map(payment, PaymentDto.class);
        paymentDto.setValue(payment.getValue().divide(currencyDto.getMultiplier(), RoundingMode.HALF_UP));
        paymentDto.setOrderId(order.getId());
        paymentDto.setClientId(order.getClient().getId());
        paymentDto.setCurrencyId(currencyDto.getId());

        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        Set<OrderItemDto> orderItemDtos = new HashSet<>();
        orderItems.forEach(orderItem -> orderItemDtos.add(modelMapper.map(orderItem, OrderItemDto.class)));

        orderItemDtos.forEach(orderItemDto ->
                orderItemDto.setProduct(
                        productService.findById(orderItemDto.getProduct().getId(), currencyId, languageId))
        );

        OrderDto orderDto = modelMapper.map(order, OrderDto.class);

        orderDto.setShipmentDto(shipmentDto);
        orderDto.setOrderItems(orderItemDtos);
        orderDto.setPaymentDto(paymentDto);
        orderDto.setCurrency(currencyDto);

        return orderDto;
    }

}
