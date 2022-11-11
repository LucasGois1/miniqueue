package org.lucasgois.domain;



import java.util.concurrent.PriorityBlockingQueue;

public class Partition {

    private String name;
    private final PriorityBlockingQueue<String> queue;

    public Partition(final String name) {
        this.name = name;
        this.queue = new PriorityBlockingQueue<>();
    }
    public String getName() {
        return name;
    }

    public String consume() {
        return queue.poll();
    }

    public void add(String message) {
        queue.add(message);
    }
}
