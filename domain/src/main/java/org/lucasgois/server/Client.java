package org.lucasgois.server;

import java.net.Socket;

public record Client(Socket socket) {

    public String getFullAddress() {
        return socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }
}
