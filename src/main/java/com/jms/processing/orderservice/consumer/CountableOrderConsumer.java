package com.jms.processing.orderservice.consumer;

import com.jms.processing.orderservice.dto.OrderDTO;
import com.jms.processing.orderservice.service.CheckLimitationOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountableOrderConsumer {
    private static final String COUNTABLE_PRODUCT_ORDER_QUEUE = "countable.product.order.queue";

    private final CheckLimitationOrderService checkLimitationOrderService;

    public CountableOrderConsumer(CheckLimitationOrderService checkLimitationOrderService) {
        this.checkLimitationOrderService = checkLimitationOrderService;
    }

    @JmsListener(destination = COUNTABLE_PRODUCT_ORDER_QUEUE)
    public void consumeCountableProducts(@Payload OrderDTO orderDTO) {

        log.info("Consume order of countable type products. OrderId = {}. Customer = {}", orderDTO.getUuid(), orderDTO.getCustomer().fullName());
        checkLimitationOrderService.handleOrder(orderDTO);
    }
}