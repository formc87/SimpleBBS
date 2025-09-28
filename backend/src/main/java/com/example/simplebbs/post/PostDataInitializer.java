package com.example.simplebbs.post;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PostDataInitializer implements CommandLineRunner {

    private final PostRepository postRepository;

    public PostDataInitializer(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

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
