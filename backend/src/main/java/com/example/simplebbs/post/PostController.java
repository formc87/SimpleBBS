package com.example.simplebbs.post;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * 실제 HTTP 요청을 받아서 어떤 작업을 해야 하는지 결정하는 컨트롤러입니다.
 * "/api/posts" 주소로 들어오는 요청을 받아 서비스 레이어를 호출하고 응답을 만들어 돌려줍니다.
 */
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    /** 비즈니스 로직을 담당하는 서비스 객체입니다. */
    private final PostService postService;

    /** 스프링이 자동으로 PostService를 주입해 줍니다. */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * GET /api/posts 요청을 처리합니다.
     * 서비스에서 전체 게시글을 받아오고, 응답용 DTO(PostResponse)로 변환해 반환합니다.
     */
    @GetMapping
    public List<PostResponse> findAll() {
        return postService.findAll().stream()
                .map(PostResponse::from)
                .toList();
    }

    /**
     * GET /api/posts/{id} 요청을 처리합니다.
     * path 변수로 받은 id에 해당하는 게시글을 조회해 응답 DTO로 변환합니다.
     */
    @GetMapping("/{id}")
    public PostResponse findById(@PathVariable Long id) {
        return PostResponse.from(postService.findById(id));
    }

    /**
     * POST /api/posts 요청을 처리합니다.
     * @Valid를 이용해 요청 본문을 검증하고, 저장이 끝나면 201 Created 응답을 돌려줍니다.
     */
    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostRequest request) {
        Post created = postService.create(request.toEntity());
        return ResponseEntity
                .created(URI.create("/api/posts/" + created.getId()))
                .body(PostResponse.from(created));
    }

    /**
     * PUT /api/posts/{id} 요청을 처리합니다.
     * 프론트에서 넘어온 수정 내용을 Post 엔티티로 바꾼 뒤 서비스에 전달합니다.
     */
    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id, @Valid @RequestBody PostRequest request) {
        Post updated = postService.update(id, request.toEntity());
        return PostResponse.from(updated);
    }

    /**
     * DELETE /api/posts/{id} 요청을 처리합니다.
     * 성공적으로 삭제하면 HTTP 204(No Content) 응답을 보냅니다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
