package com.jms.processing.orderservice.service;

import com.jms.processing.orderservice.dto.CountableOrderItemDTO;
import com.jms.processing.orderservice.dto.LiquidOrderItemDTO;
import com.jms.processing.orderservice.dto.OrderItem;

import java.util.HashMap;
import java.util.Map;

public class ProductLimitation {
    private final static Map<String, Double> LIQUID_PRODUCT_LIMITATIONS = new HashMap<>();
    private final static Map<String, Integer> COUNTABLE_PRODUCT_LIMITATIONS = new HashMap<>();

    static {
        LIQUID_PRODUCT_LIMITATIONS.put("Water", 3.5);
        LIQUID_PRODUCT_LIMITATIONS.put("Milk", 4.0);
        LIQUID_PRODUCT_LIMITATIONS.put("Bear", 1.5);
        LIQUID_PRODUCT_LIMITATIONS.put("Olive oil", 0.75);

        COUNTABLE_PRODUCT_LIMITATIONS.put("Chocolate", 2);
        COUNTABLE_PRODUCT_LIMITATIONS.put("Canned fish", 2);
        COUNTABLE_PRODUCT_LIMITATIONS.put("Bread", 4);
        COUNTABLE_PRODUCT_LIMITATIONS.put("Strawberry", 1);

    }

    private ProductLimitation() {
    }

    public static boolean isOverProductLimitation(OrderItem orderItem) {
        String productName = orderItem.getProduct().getName();
        if (!LIQUID_PRODUCT_LIMITATIONS.containsKey(productName) && !COUNTABLE_PRODUCT_LIMITATIONS.containsKey(productName)) {
            return false;
        }

        return switch (orderItem.getProduct().getType()) {
            case LIQUID-> LIQUID_PRODUCT_LIMITATIONS.get(productName) < orderItem.quantityOrderedProduct().doubleValue();
            case COUNTABLE -> COUNTABLE_PRODUCT_LIMITATIONS.get(productName) < orderItem.quantityOrderedProduct().intValue();
        };
    }
}
