package com.example.poststats.statistics;

import com.example.poststats.event.PostEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PostStatisticsService {

    private static final Logger log = LoggerFactory.getLogger(PostStatisticsService.class);

    private final Map<Long, PostSnapshot> posts = new ConcurrentHashMap<>();
    private final AtomicLong createdCount = new AtomicLong();
    private final AtomicLong updatedCount = new AtomicLong();
    private final AtomicLong deletedCount = new AtomicLong();

    @KafkaListener(topics = "${app.kafka.post-topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(PostEvent event) {
        if (event == null) {
            return;
        }
        log.debug("Consuming event {}", event);
        Instant changedAt = event.occurredAt() != null ? event.occurredAt() : Instant.now();
        PostSnapshot snapshot = new PostSnapshot(event.postId(), event.title(), event.author(), changedAt);

        if (event.eventType() == null) {
            log.warn("Received event without type: {}", event);
            return;
        }

        switch (event.eventType()) {
            case CREATED -> {
                posts.put(event.postId(), snapshot);
                createdCount.incrementAndGet();
            }
            case UPDATED -> {
                posts.compute(event.postId(), (id, existing) -> snapshot);
                updatedCount.incrementAndGet();
            }
            case DELETED -> {
                posts.remove(event.postId());
                deletedCount.incrementAndGet();
            }
            default -> log.warn("Unsupported event type: {}", event.eventType());
        }
    }

    public StatisticsSnapshot getSnapshot() {
        List<PostSnapshot> postSnapshots = posts.values().stream()
                .sorted((a, b) -> b.lastChangedAt().compareTo(a.lastChangedAt()))
                .toList();
        return new StatisticsSnapshot(
                posts.size(),
                createdCount.get(),
                updatedCount.get(),
                deletedCount.get(),
                postSnapshots
        );
    }
}
