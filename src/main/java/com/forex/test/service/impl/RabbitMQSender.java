package com.forex.test.service.impl;

import com.forex.test.domain.ExchangeRate;
import com.forex.test.domain.ExtServiceRequest;
import com.forex.test.service.dto.ExchangeRateDTO;
import com.forex.test.service.dto.ExtServiceRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.external-service-request.exchange}")
    private String extServiceExchange;

    @Value("${rabbitmq.external-service-request.routing-key}")
    private String extServiceRoutingKey;

    @Value("${rabbitmq.exchange-rates.exchange}")
    private String exRatesExchange;

    @Value("${rabbitmq.exchange-rates.routing-key}")
    private String exRatesRoutingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSender.class);

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendExtServiceRequests(ExtServiceRequestDTO extServiceRequestDTO) {
        LOGGER.info(String.format("Json message sent -> %s", extServiceRequestDTO.toString()));
        rabbitTemplate.convertAndSend(extServiceExchange, extServiceRoutingKey, extServiceRequestDTO);
    }

    public void sendExchangeRates(ExchangeRateDTO exchangeRateDTO) {
        LOGGER.info(String.format("Json message sent -> %s", exchangeRateDTO.toString()));
        rabbitTemplate.convertAndSend(exRatesExchange, exRatesRoutingKey, exchangeRateDTO);
    }
}
