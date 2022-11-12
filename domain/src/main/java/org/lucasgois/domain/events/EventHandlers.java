package org.lucasgois.domain.events;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

@SuppressWarnings("unchecked")
public class EventHandlers {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Map<String, BlockingQueue<BaseEvent<?>>> events = new ConcurrentHashMap<>();
    private final Map<String, List<Handler<BaseEvent<?>>>> handlers = new ConcurrentHashMap<>();

    public <T extends BaseEvent<?>> void addHandler(final Class<T> event, final Handler<T> handler) {
        final List<Handler<BaseEvent<?>>> handlersOfThisEvent = getHandlersOfThisEvent(event);

        handlersOfThisEvent.add((Handler<BaseEvent<?>>) handler);

        handlers.put(event.getName(), handlersOfThisEvent);

        initializeHandler(handler);
    }

    private <T extends BaseEvent<?>> List<Handler<BaseEvent<?>>> getHandlersOfThisEvent(final Class<T> event) {
        var handlersOfThisEvent = handlers.get(event.getName());

        if (Objects.isNull(handlersOfThisEvent)) {
            handlersOfThisEvent = new CopyOnWriteArrayList<>();
        }

        return handlersOfThisEvent;
    }

    void removeHandler(final Class<BaseEvent<?>> eventType, final Handler<?> handler) {
        handlers
                .get(eventType.getName())
                .remove(handler);
    }

    void submitNewEvent(final BaseEvent<?> event) {
        final BlockingQueue<BaseEvent<?>> queueOfThisEvent = getQueueOfThisEvent(event.getClass().getName());

        queueOfThisEvent.add(event);

        events.put(event.getClass().getName(), queueOfThisEvent);
    }

    <T extends BaseEvent<?>> void initializeHandler(final Handler<T> handler) {
        final var eventOfMyInterest = handler.getEventInterests();
        final var myQueue = getQueueOfThisEvent(eventOfMyInterest.getName());

        events.put(eventOfMyInterest.getName(), myQueue);

        handler.observe((BlockingQueue<T>) myQueue);

        executorService.submit(handler);
    }

    private BlockingQueue<BaseEvent<?>> getQueueOfThisEvent(final String eventOfMyInterest) {
        var myQueue = events.get(eventOfMyInterest);

        if (Objects.isNull(myQueue)) {
            myQueue = new ArrayBlockingQueue<>(100);
        }

        return myQueue;
    }

    void reset() {
        events.clear();
        handlers.clear();
        executorService.shutdownNow();
    }
}
