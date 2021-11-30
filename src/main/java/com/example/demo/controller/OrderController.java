package com.example.demo.controller;

import com.example.demo.annotation.Authorized;
import com.example.demo.vo.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Steven
 */
@RestController
@RequestMapping
public class OrderController {

    @Authorized(permission = {"create"},requestBody = Order.class)
    @PostMapping("/create")
    public Result createOrder(@RequestBody Order order){
        return Result.ok(order);

    }

    @Authorized(permission = {"create"})
    @PostMapping("/queryOrders")
    public Result queryOrders( Order order){
        return Result.ok(order);

    }
}
