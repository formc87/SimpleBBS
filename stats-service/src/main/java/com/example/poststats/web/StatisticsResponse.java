package com.example.poststats.web;

import com.example.poststats.statistics.StatisticsSnapshot;

import java.time.Instant;
import java.util.List;

public record StatisticsResponse(
        long totalPosts,
        long createdCount,
        long updatedCount,
        long deletedCount,
        List<PostSummary> posts
) {

    public static StatisticsResponse fromSnapshot(StatisticsSnapshot snapshot) {
        List<PostSummary> postSummaries = snapshot.posts().stream()
                .map(post -> new PostSummary(post.postId(), post.title(), post.author(), post.lastChangedAt()))
                .toList();
        return new StatisticsResponse(
                snapshot.totalPosts(),
                snapshot.createdCount(),
                snapshot.updatedCount(),
                snapshot.deletedCount(),
                postSummaries
        );
    }

    public record PostSummary(Long postId, String title, String author, Instant lastChangedAt) {
    }
}
