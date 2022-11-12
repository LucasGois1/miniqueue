package org.lucasgois.domain.events;

import java.time.LocalDateTime;

public record Payload<T>(
        LocalDateTime timestamp,
        T content
) {}
