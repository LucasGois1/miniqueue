package org.lucasgois;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try (final var socket = new Socket("localhost", 12456)) {

            final var output = socket.getOutputStream();
            final var inputStream = new PrintStream(output);

            while(true) {
                final var scanner = new Scanner(System.in);
                final var message = scanner.nextLine();
                inputStream.println(message);
            }
        }
    }
}