package org.lucasgois.domain;

import org.lucasgois.domain.events.*;


public class MiniQueue {

    private final EventHandlers eventHandlers;

    public MiniQueue() {
        final var newMessageEventHandler =  new NewMessageEventHandler();

        eventHandlers = new EventHandlers();
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler);
    }
}
