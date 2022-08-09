package com.jms.processing.orderservice.service;

import com.jms.processing.orderservice.dto.OrderDTO;
import com.jms.processing.orderservice.dto.OrderItem;
import com.jms.processing.orderservice.dto.OrderStatus;
import com.jms.processing.orderservice.producer.ProcessedOrderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CheckLimitationOrderServiceImpl implements CheckLimitationOrderService {
    private static final String ACCEPTED_LIQUID_PRODUCT_ORDER_QUEUE = "accepted.liquid.product.order.queue";
    private static final String REJECTED_LIQUID_PRODUCT_ORDER_QUEUE = "rejected.liquid.product.order.queue";

    private static final String ACCEPTED_COUNTABLE_PRODUCT_ORDER_QUEUE = "accepted.countable.product.order.queue";
    private static final String REJECTED_COUNTABLE_PRODUCT_ORDER_QUEUE = "rejected.countable.product.order.queue";


    private final ProcessedOrderProducer producer;

    public CheckLimitationOrderServiceImpl(ProcessedOrderProducer producer) {
        this.producer = producer;
    }

    @Override
    @Transactional
    public void handleOrder(OrderDTO orderDTO) {
        List<OrderItem> acceptedItems = new ArrayList<>();
        List<OrderItem> rejectedItems = new ArrayList<>();
        for (OrderItem orderItem : orderDTO.getOrderItems()) {
            if (ProductLimitation.isOverProductLimitation(orderItem)) {
                rejectedItems.add(orderItem);
                continue;
            }
            acceptedItems.add(orderItem);
        }

        sendAcceptedOrder(orderDTO, acceptedItems);
        sendRejectedOrder(orderDTO, rejectedItems);
    }

    private void sendAcceptedOrder(OrderDTO orderDTO, List<OrderItem> acceptedItems) {
        if (acceptedItems.isEmpty()) {
            return;
        }

        String destination = switch (orderDTO.getProductType()) {
            case LIQUID -> ACCEPTED_LIQUID_PRODUCT_ORDER_QUEUE;
            case COUNTABLE -> ACCEPTED_COUNTABLE_PRODUCT_ORDER_QUEUE;
        };
        sendOrder(orderDTO, OrderStatus.ACCEPTED, acceptedItems, destination);
    }

    private void sendRejectedOrder(OrderDTO orderDTO, List<OrderItem> rejectedItems) {
        if (rejectedItems.isEmpty()) {
            return;
        }

        String destination = switch (orderDTO.getProductType()) {
            case LIQUID -> REJECTED_LIQUID_PRODUCT_ORDER_QUEUE;
            case COUNTABLE -> REJECTED_COUNTABLE_PRODUCT_ORDER_QUEUE;
        };
        sendOrder(orderDTO, OrderStatus.REJECTED, rejectedItems, destination);
    }


    private void sendOrder(OrderDTO orderDTO,  OrderStatus orderStatus, List<OrderItem> orderItems, String destination) {
        OrderDTO handledOrderDTO = new OrderDTO(
                orderDTO.getUuid(),
                orderDTO.getCustomer(),
                orderItems,
                orderDTO.getProductType(),
                orderStatus);
        producer.sendOrder(handledOrderDTO, destination);
    }
}
