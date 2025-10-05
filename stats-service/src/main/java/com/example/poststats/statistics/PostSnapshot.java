package com.example.poststats.statistics;

import java.time.Instant;

public record PostSnapshot(
        Long postId,
        String title,
        String author,
        Instant lastChangedAt
) {
}
