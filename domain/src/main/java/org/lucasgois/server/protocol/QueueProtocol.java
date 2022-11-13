package org.lucasgois.server.protocol;


import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class QueueProtocol<T> {
    private final UUID id;
    private final String origin;
    private final T content;
    private final LocalDateTime createdAt;
    private final String typeOfContent;

    public QueueProtocol(
            final String origin,
            final T content,
            final String typeOfContent
    ) {
        this.id = UUID.randomUUID();
        this.origin = origin;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.typeOfContent = typeOfContent;
    }
}
