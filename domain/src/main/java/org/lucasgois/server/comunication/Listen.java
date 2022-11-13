package org.lucasgois.server.comunication;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.server.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class Listen implements Runnable {

    final Client client;

    public Listen(final Client client) {
        this.client = client;
    }

    @Override
    public void run() {

        try {
            final var input = this.client.socket().getInputStream();

            final var inputStream = new BufferedReader(new InputStreamReader(input));

            String inputLine;

            log.info("Listening to client: " + client.getFullAddress());
            while ((inputLine = inputStream.readLine()) != null) {
                log.info("Received: " + inputLine);

                final var parsedRequest = new ProtocolParser().parse(client, inputLine);

                log.info("Parsed: " + parsedRequest);

                RequestStore.add(parsedRequest);
            }

            log.info("End Listening to client: " + client.getFullAddress());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}