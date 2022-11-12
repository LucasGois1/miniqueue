package org.lucasgois.server;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Slf4j
public class Listen implements Runnable {

    final Socket socket;

    public Listen(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            final var input = this.socket.getInputStream();

            final var inputStream = new BufferedReader(new InputStreamReader(input));

            String inputLine;

            log.info("Listening to client: " + socket.getInetAddress().getHostAddress());
            while ((inputLine = inputStream.readLine()) != null) {
                log.info("Received: " + inputLine);
            }

            log.info("End Listening to client: " + socket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
  }
}