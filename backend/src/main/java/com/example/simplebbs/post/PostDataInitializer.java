package com.example.simplebbs.post;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션이 처음 실행될 때 샘플 데이터를 넣어 주는 초기화 클래스입니다.
 * 게시판이 비어 있으면 방문자가 허전해하므로 기본 글 두 개를 자동으로 생성합니다.
 */
@Component
public class PostDataInitializer implements CommandLineRunner {

    /** DB와 연결된 저장소입니다. */
    private final PostRepository postRepository;

    public PostDataInitializer(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * CommandLineRunner의 run 메서드는 애플리케이션 부팅이 끝난 직후 한 번 실행됩니다.
     */
    @Override
    public void run(String... args) {
        if (postRepository.count() == 0) {
            Post welcome = new Post(
                    "환영합니다!",
                    "이 게시판은 Spring Boot와 React로 만들어졌습니다.",
                    "관리자"
            );
            Post guide = new Post(
                    "첫 번째 글을 작성해보세요",
                    "새 글 작성 버튼을 눌러 자유롭게 의견을 남겨보세요.",
                    "관리자"
            );
            postRepository.save(welcome);
            postRepository.save(guide);
        }
    }
}
