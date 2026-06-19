package com.digiwallet.service.transfer.wrappers;

import com.digiwallet.service.transfer.constants.EventType;

public class EventWrapper {

    private EventType eventType;
    private String payload;

    public EventWrapper(EventType eventType, String payload){
        this.eventType = eventType;
        this.payload = payload;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    
}
