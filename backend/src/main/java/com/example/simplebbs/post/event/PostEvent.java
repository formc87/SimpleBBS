package com.example.simplebbs.post.event;

import com.example.simplebbs.post.Post;

import java.time.Instant;

/**
 * 게시글의 생성/수정/삭제와 같은 주요 이벤트 정보를 카프카로 전달하기 위한 메시지 형태입니다.
 */
public record PostEvent(
        PostEventType eventType,
        Long postId,
        String title,
        String author,
        String content,
        Instant occurredAt
) {

    /**
     * 도메인 엔티티와 이벤트 타입을 받아 이벤트 메시지를 만들어 줍니다.
     */
    public static PostEvent of(PostEventType eventType, Post post) {
        return new PostEvent(
                eventType,
                post.getId(),
                post.getTitle(),
                post.getAuthor(),
                post.getContent(),
                Instant.now()
        );
    }
}
