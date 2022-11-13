package org.lucasgois.server.comunication;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.server.protocol.QueueProtocol;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class RequestStore {
    private static BlockingQueue<QueueProtocol<?>> storedRequests;

    public static void init(final int size) {
        if (storedRequests == null) {
            storedRequests = new ArrayBlockingQueue<>(size);
        } else {
            log.info("RequestStore already initialized");
        }
    }

    public static QueueProtocol<?> get() throws InterruptedException {
        log.info("Waiting for new request...");
        return storedRequests.take();
    }

    public static void add(final QueueProtocol<?> protocol) {
        log.info("Adding new request to store: {}", protocol);
        storedRequests.offer(protocol);
    }
}
