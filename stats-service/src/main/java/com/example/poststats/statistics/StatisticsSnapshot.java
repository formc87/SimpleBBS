package com.example.poststats.statistics;

import java.util.List;

public record StatisticsSnapshot(
        long totalPosts,
        long createdCount,
        long updatedCount,
        long deletedCount,
        List<PostSnapshot> posts
) {
}
