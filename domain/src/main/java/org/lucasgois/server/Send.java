package org.lucasgois.server;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintStream;
import java.net.Socket;

@Slf4j
public class Send implements Runnable {

    final String message;
    final Socket socket;

    public Send(final String message, final Socket socket) {
        this.message = message;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            final var output = socket.getOutputStream();
            final var inputStream = new PrintStream(output);

            inputStream.println(message);

        } catch (final Exception e) {
            log.error("Error while sending to client: " + e.getMessage());

            throw new RuntimeException(e);
        }
    }
}