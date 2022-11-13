package org.lucasgois.domain.events.thread;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.domain.events.exceptions.EventNotProcessedException;
import org.lucasgois.domain.events.handlers.EventHandlers;

@Slf4j
public class EventThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final EventHandlers eventHandlers;

    public EventThreadExceptionHandler(final EventHandlers eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        log.warn("Exception in thread " + t.getName() + ": " + e.getMessage());

        if (EventNotProcessedException.class.equals(e.getClass())) {
            log.warn("Exception: {}", e.getMessage());
            final var event = ((EventNotProcessedException) e).getEvent();

            eventHandlers.resubmitEvent(event);
        } else {
            System.out.println("Unhandled exception: " + e.getClass().getName());
        }

        log.warn("Exception handled");
    }
}
