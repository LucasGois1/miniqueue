package org.lucasgois.domain.events.handlers;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.domain.events.BaseEvent;
import org.lucasgois.domain.events.thread.EventThreadExceptionHandler;
import org.lucasgois.domain.events.thread.HandlerThreadFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
@SuppressWarnings("unchecked")
public class EventHandlers {

    private final Map<String, BlockingQueue<BaseEvent<?>>> events;
    private final Map<String, List<Handler<BaseEvent<?>>>> handlers;

    private final ExecutorService executorService;

    public EventHandlers() {
        this.events = new ConcurrentHashMap<>();
        this.handlers = new ConcurrentHashMap<>();

        final var exceptionHandler = new EventThreadExceptionHandler(this);
        final var threadFactory = new HandlerThreadFactory(exceptionHandler);

        this.executorService = Executors.newFixedThreadPool(10, threadFactory);
    }


    public <T extends BaseEvent<?>> void addHandler(final Class<T> event, final Handler<T> handler) {
        log.info("Adding handler: {} for event: {}", handler, event.getSimpleName());

        final List<Handler<BaseEvent<?>>> handlersOfThisEvent = getHandlersOfThisEvent(event);

        handlersOfThisEvent.add((Handler<BaseEvent<?>>) handler);

        handlers.put(event.getName(), handlersOfThisEvent);

        initializeHandler(handler);
    }

    private <T extends BaseEvent<?>> List<Handler<BaseEvent<?>>> getHandlersOfThisEvent(final Class<T> event) {
        log.info("Retrieving all handlers interested in event: {}", event.getName());

        var handlersOfThisEvent = handlers.get(event.getName());

        if (Objects.isNull(handlersOfThisEvent)) {
            handlersOfThisEvent = new CopyOnWriteArrayList<>();
        }

        return handlersOfThisEvent;
    }

    void removeHandler(final Class<BaseEvent<?>> eventType, final Handler<?> handler) {
        log.info("Removing handler: {}", handler);

        handlers
                .get(eventType.getName())
                .remove(handler);
    }

    public void submitNewEvent(final BaseEvent<?> event) {
        log.info("Adding new event to your respective Queue: {}", event);

        final BlockingQueue<BaseEvent<?>> queueOfThisEvent = getQueueOfThisEvent(event.getClass().getName());

        queueOfThisEvent.offer(event);

        events.put(event.getClass().getName(), queueOfThisEvent);
    }

    public void resubmitEvent(final BaseEvent<?> event) {
        log.info("Trying to resubmit event: {}", event);

        final BlockingQueue<BaseEvent<?>> queueOfThisEvent = getQueueOfThisEvent(event.getClass().getName());

        final var resubmitTask = new ResubmitEventTask(event, queueOfThisEvent);

        log.info("ResubmitEventTask created: {}", resubmitTask);

        executorService.execute(resubmitTask);
    }

    <T extends BaseEvent<?>> void initializeHandler(final Handler<T> handler) {
        log.info("Initializing handler: {}", handler.getClass().getSimpleName());

        final var eventOfMyInterest = handler.getEventInterests();
        final var myQueue = getQueueOfThisEvent(eventOfMyInterest.getName());

        events.put(eventOfMyInterest.getName(), myQueue);

        handler.observe((BlockingQueue<T>) myQueue);

        executorService.execute(handler);
    }

    private BlockingQueue<BaseEvent<?>> getQueueOfThisEvent(final String eventOfMyInterest) {
        log.info("Getting queue of event: {}", eventOfMyInterest);

        var myQueue = events.get(eventOfMyInterest);

        if (Objects.isNull(myQueue)) {
            myQueue = new ArrayBlockingQueue<>(100);
        }

        return myQueue;
    }

    void reset() {
        log.info("Finishing service");

        events.clear();
        handlers.clear();
        executorService.shutdownNow();
    }
}
