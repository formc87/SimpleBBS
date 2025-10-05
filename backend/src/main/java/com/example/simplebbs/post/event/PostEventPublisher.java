package com.example.simplebbs.post.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 게시판 서비스에서 발생한 이벤트를 카프카 토픽으로 발행하는 컴포넌트입니다.
 */
@Component
public class PostEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(PostEventPublisher.class);

    private final KafkaTemplate<String, PostEvent> kafkaTemplate;
    private final String topic;

    public PostEventPublisher(
            KafkaTemplate<String, PostEvent> kafkaTemplate,
            @Value("${app.kafka.post-topic}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    /**
     * 전달받은 이벤트를 카프카 토픽으로 비동기 전송합니다.
     */
    public void publish(PostEvent event) {
        kafkaTemplate.send(topic, event.postId().toString(), event)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.error("Failed to publish post event: {}", event, throwable);
                        return;
                    }

                    if (result != null && log.isDebugEnabled()) {
                        log.debug("Published post event to topic {} partition {} offset {}", topic,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
