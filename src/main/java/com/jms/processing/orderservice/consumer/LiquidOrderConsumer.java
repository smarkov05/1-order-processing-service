package com.jms.processing.orderservice.consumer;

import com.jms.processing.orderservice.dto.OrderDTO;
import com.jms.processing.orderservice.service.CheckLimitationOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LiquidOrderConsumer {
    private static final String LIQUID_PRODUCT_ORDER_QUEUE = "liquid.product.order.queue";

    private final CheckLimitationOrderService checkLimitationOrderService;

    public LiquidOrderConsumer(CheckLimitationOrderService checkLimitationOrderService) {
        this.checkLimitationOrderService = checkLimitationOrderService;
    }

    @JmsListener(destination = LIQUID_PRODUCT_ORDER_QUEUE)
    public void consumeCountableProducts(@Payload OrderDTO orderDTO) {

        log.info("Consume order of liquid type products. OrderId = {}. Customer = {}", orderDTO.getUuid(), orderDTO.getCustomer().fullName());
        checkLimitationOrderService.handleOrder(orderDTO);
    }
}
