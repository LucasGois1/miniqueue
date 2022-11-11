package org.lucasgois.domain.events;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class NewMessageEventHandler extends Handler<NewMessageReceived>{

    @Override
    public void handle(final NewMessageReceived event) {

        log.info("Handling event: {}", event);

        try {
            log.info("Processing event: {}", event);
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("End of processing event: {}", event);
    }

    @Override
    public Class<NewMessageReceived> getEventInterests() {
        return NewMessageReceived.class;
    }

}
