package org.lucasgois;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("InfiniteLoopStatement")
public class Main {
    public static void main(String[] args) throws IOException {
        try (final var socket = new Socket("localhost", 12345)) {

            final var output = socket.getOutputStream();
            final var outputStream = new PrintStream(output);

            System.out.println("Connected to server");

            while(true) {
                System.out.println("Enter a message to send to the server: ");
                final var scanner = new Scanner(System.in);
                final var message = scanner.nextLine();

                outputStream.println(message);
            }
        }
    }
}