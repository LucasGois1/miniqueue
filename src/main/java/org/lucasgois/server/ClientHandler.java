package org.lucasgois.server;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.util.concurrent.Callable;

@Slf4j
public class ClientHandler implements Callable<Void> {

    private final Socket socket;

    public ClientHandler(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public Void call() {
        new Thread(new Listen(socket)).start();
        new Thread(new Send("We are able to send messages to you", socket)).start();

        return null;
    }
}
