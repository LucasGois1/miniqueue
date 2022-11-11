package org.lucasgois.domain.events;


import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEvent<T> {

    protected final UUID id;
    protected final LocalDateTime timestamp;

    protected BaseEvent() {
        this.id = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
    }

    abstract Payload<T> getContent();
}
