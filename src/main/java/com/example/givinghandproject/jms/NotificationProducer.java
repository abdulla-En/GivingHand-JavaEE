package com.example.givinghandproject.jms;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.time.LocalDateTime;
import jakarta.jms.Queue;

@Stateless
public class NotificationProducer {
    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/NotificationQueue")
    private Queue queue;

    public void sendEvent(String eventName, String message, String email) {
        JsonObject event = Json.createObjectBuilder()
                .add("eventName", eventName)
                .add("message", message)
                .add("targetEmail", email)
                .add("timestamp", LocalDateTime.now().toString())
                .build();
        context.createProducer().send((Destination) queue, event.toString());
    }
}