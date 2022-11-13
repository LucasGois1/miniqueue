package org.lucasgois.domain.events.handlers;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.lucasgois.domain.events.BaseEvent;

import java.util.concurrent.BlockingQueue;

@Slf4j
@ToString
@SuppressWarnings("InfiniteLoopStatement")
public abstract class Handler<T extends BaseEvent<?>> implements Runnable {

    private BlockingQueue<T> newEvents;

    @Override
    public void run() {

        try {
            log.info("Starting handler: {}", this.getClass().getSimpleName());

            while (true) {
                log.info("Waiting for new events");
                T event = newEvents.take();

                log.info("New event received: {}", event);
                handle(event);
            }

        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    abstract void handle(T event);

    public void observe(final BlockingQueue<T> queue) {
        this.newEvents = queue;
    }

    public abstract Class<T> getEventInterests();
}
