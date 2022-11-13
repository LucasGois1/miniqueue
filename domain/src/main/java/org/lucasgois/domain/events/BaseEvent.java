package org.lucasgois.domain.events;


import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEvent<T> {

    private int retryCount = 0;
    private long retryTimeCount = 1000;
    protected final UUID id;
    protected final LocalDateTime timestamp;

    protected BaseEvent() {
        this.id = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
    }

    abstract Payload<T> getContent();

    public void incrementRetryTimeCount() {
        retryCount++;
        retryTimeCount = retryTimeCount * 2;
    }

    public long getRetryTimeCount() {
        return retryTimeCount;
    }

    public boolean maxRetriesReached() {
        return retryCount >= 10;
    }
}
