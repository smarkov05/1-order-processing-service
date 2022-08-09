package com.jms.processing.orderservice.producer;

import com.jms.processing.orderservice.dto.OrderDTO;

public interface ProcessedOrderProducer {
    void sendOrder(OrderDTO orderDTO, String destination);

}
