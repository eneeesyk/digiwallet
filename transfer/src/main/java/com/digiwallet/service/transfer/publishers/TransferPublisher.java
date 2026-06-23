package com.digiwallet.service.transfer.publishers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.digiwallet.service.transfer.constants.EventType;
import com.digiwallet.service.transfer.wrappers.EventWrapper;
import com.google.gson.Gson;

@Component
public class TransferPublisher {
    
    private final RabbitTemplate rabbitTemplate;

    private Gson gson = new Gson();

    public TransferPublisher(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessage(String exchange, String routingKey, Object payload, EventType eventType){
        EventWrapper eventWrapper = new EventWrapper(eventType, gson.toJson(payload));
        rabbitTemplate.convertAndSend(exchange, routingKey, gson.toJson(eventWrapper));
        System.out.println("Transfer Message published to exchange: " + eventWrapper.toString());
   }
}
