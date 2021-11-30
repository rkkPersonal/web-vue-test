package com.example.demo.vo;

import lombok.Data;

/**
 * @author Steven
 */
@Data
public class Order {

    private Long orderId;

    private String shoppingName;

    private Shipping shipping;

}
