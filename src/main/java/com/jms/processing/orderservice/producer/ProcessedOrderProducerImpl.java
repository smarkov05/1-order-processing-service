package com.jms.processing.orderservice.producer;

import com.jms.processing.orderservice.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessedOrderProducerImpl implements ProcessedOrderProducer {

    private final JmsTemplate jmsTemplate;

    public ProcessedOrderProducerImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrder(OrderDTO orderDTO, String destination) {

        jmsTemplate.convertAndSend(destination, orderDTO, message -> {
            message.setStringProperty("orderId", orderDTO.getUuid().toString());
            message.setStringProperty("orderType", orderDTO.getProductType().name());
            message.setStringProperty("customerFullName", orderDTO.getCustomer().fullName());
            return message;
        });
        log.info("Order was sent to queue = {}! {}", destination, orderDTO);
    }
}


