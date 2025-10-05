package com.example.poststats.event;

import java.time.Instant;

public record PostEvent(
        PostEventType eventType,
        Long postId,
        String title,
        String author,
        String content,
        Instant occurredAt
) {
}
