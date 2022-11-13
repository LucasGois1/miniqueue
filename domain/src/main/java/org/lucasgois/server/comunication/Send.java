package org.lucasgois.server.comunication;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.server.Client;

import java.io.PrintStream;

@Slf4j
public class Send implements Runnable {

    final String message;
    final Client client;

    public Send(final String message, final Client client) {
        this.message = message;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            final var output = client.socket().getOutputStream();
            final var inputStream = new PrintStream(output);

            inputStream.println(message);

        } catch (final Exception e) {
            log.error("Error while sending to client: {} | ERROR: {}", client.socket(), e.getMessage());

            throw new RuntimeException(e);
        }
    }
}