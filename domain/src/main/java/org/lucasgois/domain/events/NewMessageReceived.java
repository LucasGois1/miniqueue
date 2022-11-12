package org.lucasgois.domain.events;


public class NewMessageReceived extends BaseEvent<String> {

    private final String content;

    public NewMessageReceived(final String content) {
        this.content = content;
    }

    @Override
    Payload<String> getContent() {
        return new Payload<>(timestamp, content);
    }

    @Override
    public String toString() {
        return "NewMessageReceived{" +
                "content='" + content + '\'' +
                ", id=" + id +
                '}';
    }
}
