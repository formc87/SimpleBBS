package com.example.simplebbs.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequest(
        @NotBlank(message = "제목을 입력하세요.")
        @Size(max = 150, message = "제목은 150자 이하로 입력하세요.")
        String title,

        @NotBlank(message = "내용을 입력하세요.")
        String content,

        @NotBlank(message = "작성자를 입력하세요.")
        @Size(max = 50, message = "작성자는 50자 이하로 입력하세요.")
        String author
) {
    public Post toEntity() {
        return new Post(title, content, author);
    }
}
