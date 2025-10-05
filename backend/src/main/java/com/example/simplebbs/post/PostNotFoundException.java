package com.example.simplebbs.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 요청한 게시글이 존재하지 않을 때 던지는 사용자 정의 예외입니다.
 * @ResponseStatus 덕분에 이 예외가 발생하면 자동으로 404 상태 코드를 내려줍니다.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Post not found with id=" + id);
    }
}
