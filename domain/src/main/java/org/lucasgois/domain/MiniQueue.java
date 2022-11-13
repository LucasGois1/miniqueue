package org.lucasgois.domain;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.domain.events.handlers.EventHandlers;
import org.lucasgois.domain.events.handlers.NewMessageEventHandler;
import org.lucasgois.domain.events.NewMessageReceived;
import org.lucasgois.server.comunication.RequestStore;
import org.lucasgois.server.protocol.QueueProtocol;

@Slf4j
@SuppressWarnings({"InfiniteLoopStatement", "unchecked"})
public class MiniQueue implements Runnable {

    @Override
    public void run() {
        log.info("Initializing MiniQueue...");

        final var newMessageEventHandler1 = new NewMessageEventHandler();
        final var newMessageEventHandler2 = new NewMessageEventHandler();
        final var newMessageEventHandler3 = new NewMessageEventHandler();
        final var newMessageEventHandler4 = new NewMessageEventHandler();
        final var newMessageEventHandler5 = new NewMessageEventHandler();
        final var newMessageEventHandler6 = new NewMessageEventHandler();
        final var newMessageEventHandler7 = new NewMessageEventHandler();
        final var newMessageEventHandler8 = new NewMessageEventHandler();


        final var eventHandlers = new EventHandlers();
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler1);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler2);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler3);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler4);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler5);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler6);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler7);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler8);

        QueueProtocol<String> newRequest;

        while (true) {
            log.info("Waiting for new request...");
            try {
                newRequest = (QueueProtocol<String>) RequestStore.get();
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }

            final var newMessageReceived = new NewMessageReceived(newRequest.getContent());
            eventHandlers.submitNewEvent(newMessageReceived);
        }
    }
}
