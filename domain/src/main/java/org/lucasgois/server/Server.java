package org.lucasgois.server;

import lombok.extern.slf4j.Slf4j;

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

            log.info("Client connected: " + socket.getInetAddress().getHostAddress());

            dispatch(socket);
        }
    }

    private void dispatch(final Socket socket) {
        pool.submit(new ClientHandler(socket));

        log.info("Client dispatched: " + socket.getInetAddress().getHostAddress());
    }
}
