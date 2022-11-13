package org.lucasgois.domain.events.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class HandlerThreadFactory implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Thread.UncaughtExceptionHandler exceptionHandler;

    public HandlerThreadFactory(final Thread.UncaughtExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public Thread newThread(final Runnable r) {

        final var t = new Thread(r, "MiniQueueThread-" + counter.getAndIncrement());
        t.setUncaughtExceptionHandler(exceptionHandler);

        log.info("Created new thread: {}", t.getName());
        return t;
    }
}
