package com.example.simplebbs.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 클라이언트(React 앱)에서 넘어오는 게시글 작성/수정 요청을 담는 DTO입니다.
 * record 문법을 사용하면 불변 객체를 간단히 표현할 수 있습니다.
 */
public record PostRequest(
        // 제목은 필수이며 150자를 넘으면 검증 단계에서 막습니다.
        @NotBlank(message = "제목을 입력하세요.")
        @Size(max = 150, message = "제목은 150자 이하로 입력하세요.")
        String title,

        // 내용도 필수 항목입니다.
        @NotBlank(message = "내용을 입력하세요.")
        String content,

        // 작성자 정보 역시 필수이며, 너무 긴 이름은 제한합니다.
        @NotBlank(message = "작성자를 입력하세요.")
        @Size(max = 50, message = "작성자는 50자 이하로 입력하세요.")
        String author
) {
    /**
     * 서비스에서 사용할 수 있도록 요청 데이터를 Post 엔티티로 변환합니다.
     */
    public Post toEntity() {
        return new Post(title, content, author);
    }
}
