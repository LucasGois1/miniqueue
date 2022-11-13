package org.lucasgois.domain.events.handlers;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.domain.events.BaseEvent;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class ResubmitEventTask implements Runnable {

    private final BaseEvent<?> event;
    private final BlockingQueue<BaseEvent<?>> queueOfThisEvent;

    public ResubmitEventTask(final BaseEvent<?> event, final BlockingQueue<BaseEvent<?>> queueOfThisEvent) {
        this.event = event;
        this.queueOfThisEvent = queueOfThisEvent;
    }

    @Override
    public void run() {
        event.incrementRetryTimeCount();

        if (event.maxRetriesReached()) {
            log.info("Max retries reached for event: {} | Storing in database for later analysis", event);
            return;
        }

        try {
            log.info("Resubmitting event: {}", event);
            Thread.sleep(event.getRetryTimeCount());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        queueOfThisEvent.offer(event);

        log.info("Resubmitted event: {}", event);
    }
}
