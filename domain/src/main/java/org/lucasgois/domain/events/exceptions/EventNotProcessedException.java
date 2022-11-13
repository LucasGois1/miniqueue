package org.lucasgois.domain.events.exceptions;

import lombok.ToString;
import org.lucasgois.domain.events.BaseEvent;

@ToString
public class EventNotProcessedException extends RuntimeException {

    private final BaseEvent<?> event;

    public EventNotProcessedException(final BaseEvent<?> event) {
        super("Event not processed: " + event);

        this.event = event;
    }

    public BaseEvent<?> getEvent() {
        return event;
    }
}
