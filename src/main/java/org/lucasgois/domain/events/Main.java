package org.lucasgois.domain.events;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        final var newMessageEventHandler1 = new NewMessageEventHandler();
        final var newMessageEventHandler2 = new NewMessageEventHandler();
        final var newMessageEventHandler3 = new NewMessageEventHandler();

        final var eventHandlers = new EventHandlers();
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler1);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler2);
        eventHandlers.addHandler(NewMessageReceived.class, newMessageEventHandler3);

        Thread.sleep(2000);

        final var newMessageReceived = new NewMessageReceived("Hello World");

        eventHandlers.submitNewEvent(newMessageReceived);

        Thread.sleep(3000);

        final var newMessageReceived2 = new NewMessageReceived("Hello Insiders!");

        eventHandlers.submitNewEvent(newMessageReceived2);

        Thread.sleep(2000);

        final var newMessageReceived3 = new NewMessageReceived("Ultima mensagem :(");

        eventHandlers.submitNewEvent(newMessageReceived3);

        Thread.sleep(3000);

        final var newMessageReceived4 = new NewMessageReceived("Tchauuu");

        eventHandlers.submitNewEvent(newMessageReceived4);

        Thread.sleep(2000);

        final var newMessageReceived5 = new NewMessageReceived("Opa tem mais um!");

        eventHandlers.submitNewEvent(newMessageReceived5);

        Thread.sleep(1000);

        final var newMessageReceived6 = new NewMessageReceived("E outro!");

        eventHandlers.submitNewEvent(newMessageReceived6);

        Thread.sleep(2000);

        final var newMessageReceived7 = new NewMessageReceived("Agora acabou mesmo kkk");

        eventHandlers.submitNewEvent(newMessageReceived7);
    }
}
