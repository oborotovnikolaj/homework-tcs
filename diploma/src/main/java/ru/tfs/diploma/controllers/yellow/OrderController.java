package ru.tfs.diploma.controllers.yellow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tfs.diploma.dto.yellow.OrderDto;
import ru.tfs.diploma.services.yellow.OrderService;


@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public OrderDto get(@PathVariable Long id, @RequestParam Long currencyId, @RequestParam Long languageId) {
        return orderService.findById(id, currencyId, languageId);
    }

    @PostMapping
    public OrderDto create(@RequestBody OrderDto orderDto, @RequestParam Long currencyId) {
        return orderService.save(orderDto, currencyId);
    }

    @DeleteMapping
    @GetMapping("/{id}")
    public void remove(@PathVariable Long id) {
        orderService.delete(id);
    }

}
