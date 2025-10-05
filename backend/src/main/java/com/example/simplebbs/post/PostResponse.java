package com.example.simplebbs.post;

import java.time.LocalDateTime;

/**
 * API 응답으로 나가는 게시글 정보를 담는 DTO입니다.
 * 프론트엔드가 필요한 필드만 추려서 내려주기 때문에 엔티티를 그대로 노출하는 것보다 안전합니다.
 */
public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    /**
     * Post 엔티티를 받아 같은 내용의 응답 객체를 만들어주는 편의 메서드입니다.
     */
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
