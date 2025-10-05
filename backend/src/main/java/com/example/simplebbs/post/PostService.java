package com.example.simplebbs.post;

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

    /** 생성자 주입 방식으로 스프링이 자동으로 PostRepository 구현체를 넣어줍니다. */
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
        return postRepository.save(post);
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
        return postRepository.save(existing);
    }

    /**
     * 게시글을 삭제합니다.
     * 삭제 전에 existsById로 존재 여부를 확인해 존재하지 않으면 404 예외를 던집니다.
     */
    public void delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        postRepository.deleteById(id);
    }
}
