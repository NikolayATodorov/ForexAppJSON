package com.forex.test.service.impl;

import com.forex.test.domain.ExtServiceRequest;
import com.forex.test.service.dto.ExtServiceRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSender.class);

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //    public void send(ExtServiceRequestDTO extServiceRequestDTO) {
    //        rabbitTemplate.convertAndSend(exchange, routingkey, extServiceRequestDTO);
    //    }
    public void send(ExtServiceRequest extServiceRequest) {
        LOGGER.info(String.format("Json message sent -> %s", extServiceRequest.toString()));
        rabbitTemplate.convertAndSend(exchange, routingkey, extServiceRequest);
    }
}
