package com.digital.wallet.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.FanoutExchange;

@Configuration
public class RabbitMqFanoutExchangeConfig {

    public static final String FANOUT_EXCHANGE = "wallet.events";
    

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

}
