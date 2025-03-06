package org.example.web.controller;

import lombok.RequiredArgsConstructor;
import org.example.tuling.jvm.User;
import org.example.web.dao.entity.Order;
import org.example.web.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenxuegui
 * @since 2025/3/6
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @RequestMapping("/createOder")
    public Order createOder(Long uid, Long orderId)  {
        return orderService.createOder(uid,orderId);
    }

    @RequestMapping("/getOrders")
    public List<Order> getOrders(Long uid)  {
        return orderService.getOrders(uid);
    }
}
