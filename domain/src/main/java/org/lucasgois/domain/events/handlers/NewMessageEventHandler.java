package org.lucasgois.domain.events.handlers;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.lucasgois.domain.events.NewMessageReceived;
import org.lucasgois.domain.events.exceptions.EventNotProcessedException;

@Slf4j
@ToString
public class NewMessageEventHandler extends Handler<NewMessageReceived> {

    @Override
    public void handle(final NewMessageReceived event) {

        log.info("Handling event: {}", event);

        log.info("Processing event: {}", event);
        try {
            Thread.sleep(8000);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

        throw new EventNotProcessedException(event);
    }

    @Override
    public Class<NewMessageReceived> getEventInterests() {
        return NewMessageReceived.class;
    }

}
