package com.jms.processing.orderservice.service;

import com.jms.processing.orderservice.dto.OrderDTO;

public interface CheckLimitationOrderService {
    void handleOrder(OrderDTO orderDTO);
}
