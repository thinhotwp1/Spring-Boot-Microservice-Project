package com.learn.messageservice.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    // Khai báo các object
    public static final String QUEUE_A = "queue.A";
    public static final String ROUTING_A = "routing.A";
    public static final String DIRECT_EXCHANGE = "exchange.direct";


    @Bean
    Queue queueA() {
        return new Queue(QUEUE_A, false);
    }
    @Bean
    DirectExchange exchange_direct() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

//      DirectExchange binding
    @Bean
    Binding bindingA(Queue queueA, DirectExchange exchange_direct) {
        return BindingBuilder.bind(queueA).to(exchange_direct).with(ROUTING_A);
    }

    // Dùng chung các hàm convert message và rabbit template
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
