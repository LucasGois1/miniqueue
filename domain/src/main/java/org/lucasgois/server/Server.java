package org.lucasgois.server;

import lombok.extern.slf4j.Slf4j;
import org.lucasgois.server.comunication.ClientComunicationInitilizer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class Server {

    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private static boolean serverIsRunning = false;

    public Server(final int port, final int poolsize) throws Exception {


        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolsize);
        serverIsRunning = true;

        log.info("Server is running on port: " + port);
    }

    public void start() throws Exception {
        while (serverIsRunning) {
            final Socket socket = serverSocket.accept();

            final var client = new Client(socket);

            log.info("Client connected: " + client.getFullAddress());

            dispatch(client);
        }
    }

    private void dispatch(final Client client) {
        pool.submit(new ClientComunicationInitilizer(client));

        log.info("Client dispatched: " + client.getFullAddress());
    }
}
