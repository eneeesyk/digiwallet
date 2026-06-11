package com.digiwallet.service.wrappers;

public class EventWrapper {

    private String eventType;
    private String payload;

    public EventWrapper(String eventType, String payload){
        this.eventType = eventType;
        this.payload = payload;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    
}
