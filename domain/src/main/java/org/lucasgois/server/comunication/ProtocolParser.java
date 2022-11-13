package org.lucasgois.server.comunication;

import org.lucasgois.server.Client;
import org.lucasgois.server.protocol.QueueProtocol;


public class ProtocolParser {
    public QueueProtocol<?> parse(Client client, String message) {
        final var classOfMessage = detectType(message);

        if (classOfMessage == String.class) {
            return parseString(client, message);
        }
        return null;
    }

    private Class<?> detectType(String message) {
        // String detector
        return String.class;
        // Json detector
        // XML detector etc...
    }

    public QueueProtocol<String> parseString(final Client client, final String message) {
        return new QueueProtocol<>(
                client.getFullAddress(),
                message,
                message.getClass().getName()
        );
    }
}
