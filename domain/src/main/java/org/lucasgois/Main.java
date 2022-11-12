package org.lucasgois;

import org.lucasgois.server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        new Server(12345, 4).start();
    }
}