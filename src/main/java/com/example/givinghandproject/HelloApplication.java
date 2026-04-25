package com.example.givinghandproject;

import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSDestinationDefinitions;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@JMSDestinationDefinitions({
        @JMSDestinationDefinition(
                name = "java:/jms/queue/NotificationQueue",
                interfaceName = "jakarta.jms.Queue",
                destinationName = "NotificationQueue"
        )
})
@ApplicationPath("/api")
public class HelloApplication extends Application {

}