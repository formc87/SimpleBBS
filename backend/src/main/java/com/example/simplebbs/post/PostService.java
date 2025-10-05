package com.example.simplebbs.post;

import com.example.simplebbs.post.event.PostEvent;
import com.example.simplebbs.post.event.PostEventPublisher;
import com.example.simplebbs.post.event.PostEventType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 게시글과 관련된 비즈니스 로직을 담당하는 서비스 레이어입니다.
 * 컨트롤러가 직접 DB를 만지지 않고, 모든 흐름을 이 클래스를 거쳐 처리하도록 구성했습니다.
 */
@Service
@Transactional
public class PostService {

    /** 실제 DB 처리를 담당하는 JPA 저장소를 주입받습니다. */
    private final PostRepository postRepository;

    /** 게시글 이벤트를 외부로 발행하는 퍼블리셔입니다. */
    private final PostEventPublisher postEventPublisher;

    /** 생성자 주입 방식으로 스프링이 자동으로 필요한 의존성을 넣어줍니다. */
    public PostService(PostRepository postRepository, PostEventPublisher postEventPublisher) {
        this.postRepository = postRepository;
        this.postEventPublisher = postEventPublisher;
    }

    /** 모든 게시글을 최신 순서와 상관없이 통째로 가져옵니다. */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * ID로 게시글을 조회합니다.
     * 존재하지 않는 경우에는 사용자 정의 예외를 던져 컨트롤러에서 404 응답을 보내도록 합니다.
     */
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    /** 새 게시글을 DB에 저장합니다. */
    public Post create(Post post) {
        Post saved = postRepository.save(post);
        postEventPublisher.publish(PostEvent.of(PostEventType.CREATED, saved));
        return saved;
    }

    /**
     * 기존 게시글을 수정합니다.
     * 먼저 findById로 현재 데이터를 확인한 뒤, 필요한 필드만 덮어쓰고 저장합니다.
     */
    public Post update(Long id, Post updated) {
        Post existing = findById(id);
        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());
        existing.setAuthor(updated.getAuthor());
        Post saved = postRepository.save(existing);
        postEventPublisher.publish(PostEvent.of(PostEventType.UPDATED, saved));
        return saved;
    }

    /**
     * 게시글을 삭제합니다. 삭제 대상이 없으면 findById에서 404 예외를 던집니다.
     */
    public void delete(Long id) {
        Post existing = findById(id);
        postRepository.delete(existing);
        postEventPublisher.publish(PostEvent.of(PostEventType.DELETED, existing));
    }
}
