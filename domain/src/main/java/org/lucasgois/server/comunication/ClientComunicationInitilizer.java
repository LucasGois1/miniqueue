package org.lucasgois.server.comunication;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.server.Client;

import java.util.concurrent.Callable;

@Slf4j
public class ClientComunicationInitilizer implements Callable<Void> {

    private final Client client;

    public ClientComunicationInitilizer(final Client client) {
        this.client = client;
    }

    @Override
    public Void call() {
        new Thread(new Listen(client)).start();
        new Thread(new Send("We are able to send messages to you", client)).start();

        return null;
    }
}
