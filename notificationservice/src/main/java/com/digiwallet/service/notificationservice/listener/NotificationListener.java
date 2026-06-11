package com.digiwallet.service.notificationservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.digiwallet.service.constants.EventType;
import com.digiwallet.service.notificationservice.config.RabbitMqConfig;
import com.digiwallet.service.wrappers.EventWrapper;
import com.google.gson.Gson;

@Component
public class NotificationListener {

    private Gson gson = new Gson();
    
    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        EventWrapper eventWrapper = gson.fromJson(message, EventWrapper.class);

        if (EventType.MONEY_WITHDRAWN.equals(eventWrapper.getEventType())) {
            System.out.println("Money withdrawn");
        }else if (EventType.MONEY_DEPOSITED.equals(eventWrapper.getEventType())) {
            System.out.println("MoneyDeposited");
        }
    }
}
