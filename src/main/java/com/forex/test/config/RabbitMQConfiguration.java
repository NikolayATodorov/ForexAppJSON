package com.forex.test.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.external-service-request.queue}")
    private String extServiceQueue;

    @Value("${rabbitmq.external-service-request.exchange}")
    private String extServiceExchange;

    @Value("${rabbitmq.external-service-request.routing-key}")
    private String extServiceRoutingKey;

    @Value("${rabbitmq.exchange-rates.queue}")
    private String exRatesQueue;

    @Value("${rabbitmq.exchange-rates.exchange}")
    private String exRatesExchange;

    @Value("${rabbitmq.exchange-rates.routing-key}")
    private String exRatesRoutingKey;

    // spring bean for queue for external-service-request
    @Bean
    public Queue extServiceQueue() {
        return new Queue(extServiceQueue);
    }

    // spring bean for rabbitmq exchange for external-service-request
    @Bean
    public TopicExchange extServiceExchange() {
        return new TopicExchange(extServiceExchange);
    }

    // binding between external-service-request queue and exchange using routing key
    @Bean
    public Binding extServiceBinding() {
        return BindingBuilder.bind(extServiceQueue()).to(extServiceExchange()).with(extServiceRoutingKey);
    }

    // spring bean for queue for exchange-rates
    @Bean
    public Queue exRatesQueue() {
        return new Queue(exRatesQueue);
    }

    // spring bean for rabbitmq exchange for exchange-rates
    @Bean
    public TopicExchange exRatesExchange() {
        return new TopicExchange(exRatesExchange);
    }

    // binding between exchange-rates queue and exchange using routing key
    @Bean
    public Binding exRatesBinding() {
        return BindingBuilder.bind(exRatesQueue()).to(exRatesExchange()).with(exRatesRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
