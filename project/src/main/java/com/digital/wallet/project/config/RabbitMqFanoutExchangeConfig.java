package com.digital.wallet.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.FanoutExchange;

@Configuration
public class RabbitMqFanoutExchangeConfig {

    public static final String FANOUT_EXCHANGE = "wallet.events";

    public static final String TRANSFER_QUEUE_NAME = "account.transfer.queue";
    

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(TRANSFER_QUEUE_NAME);
    }

    @Bean
    public Binding transferBinding(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

}
