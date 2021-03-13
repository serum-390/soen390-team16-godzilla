package ca.serum390.godzilla.util.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ERPEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(ERPEvent event) {
        System.out.println("Publishing custom event. ");
        applicationEventPublisher.publishEvent(event);
    }
}