package org.lucasgois;

import org.lucasgois.domain.MiniQueue;
import org.lucasgois.server.Server;
import org.lucasgois.server.comunication.RequestStore;

public class Main {
    public static void main(String[] args) throws Exception {
        RequestStore.init(100);
        final var miniqueue = new Thread(new MiniQueue());
        miniqueue.start();

        new Server(12345, 4).start();

        miniqueue.join();
    }
}