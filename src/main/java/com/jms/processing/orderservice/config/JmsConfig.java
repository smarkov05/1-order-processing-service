package com.jms.processing.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.jms.processing.orderservice.dto.CountableOrderItemDTO;
import com.jms.processing.orderservice.dto.CountableProductDTO;
import com.jms.processing.orderservice.dto.LiquidOrderItemDTO;
import com.jms.processing.orderservice.dto.LiquidProductDTO;
import com.jms.processing.orderservice.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableJms
@EnableTransactionManagement
@Configuration
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        ObjectMapper om = new ObjectMapper();
        om.registerSubtypes(
                new NamedType(LiquidOrderItemDTO.class, "LiquidOrderItem"),
                new NamedType(CountableOrderItemDTO.class, "CountableOrderItem"),
                new NamedType(LiquidProductDTO.class, "LiquidProduct"),
                new NamedType(CountableProductDTO.class, "CountableProduct")
        );
        converter.setObjectMapper(om);

        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("Order", OrderDTO.class);
        converter.setTypeIdMappings(typeIdMappings);

        return converter;
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {

        CachingConnectionFactory factory = new CachingConnectionFactory(
                new ActiveMQConnectionFactory(user, password, brokerUrl)
        );
        factory.setClientId("OrderProcessor");
        factory.setSessionCacheSize(100);
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        factory.setTransactionManager(jmsTransactionManager());
        factory.setErrorHandler(t -> {
            log.error("Transaction for message was failed! Error: {}", t.getMessage());
        });
        return factory;
    }

    @Bean
    public PlatformTransactionManager jmsTransactionManager() {
        return new JmsTransactionManager(connectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;

    }

}
