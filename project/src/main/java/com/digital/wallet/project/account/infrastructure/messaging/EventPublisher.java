package com.digital.wallet.project.account.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.digital.wallet.project.account.domain.events.DomainEvent;
import com.digital.wallet.project.account.infrastructure.messaging.wrappers.EventWrapper;
import com.digital.wallet.project.account.infrastructure.serializers.EventSerializer;
import com.digital.wallet.project.constants.EventType;
import com.google.gson.Gson;

@Component
public class EventPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    private final EventSerializer eventSerializer;

    private Gson gson = new Gson();

    public EventPublisher(RabbitTemplate rabbitTemplate, EventSerializer eventSerializer) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventSerializer = eventSerializer;
    }

    public void publishMessage(String exchange, DomainEvent message, String routingKey){
        String messageJson = eventSerializer.serialize(message);
        EventWrapper eventWrapper = new EventWrapper(message.getType(), messageJson);
        rabbitTemplate.convertAndSend(exchange, routingKey, gson.toJson(eventWrapper));
        System.out.println("Message published to exchange: " + messageJson);
    }

    public void publishTransferEvent(String exchange, String routingKey, Object payload, EventType eventType){
        String messageJson = gson.toJson(payload);
        EventWrapper eventWrapper = new EventWrapper(eventType, messageJson);

        rabbitTemplate.convertAndSend(exchange, routingKey, gson.toJson(eventWrapper));
        System.out.println("Message published to Transfer Event: " + messageJson);

    }
}
