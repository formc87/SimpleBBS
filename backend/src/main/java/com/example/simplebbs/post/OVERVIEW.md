# com.example.simplebbs.post 패키지 개요

게시글(Post) 도메인의 모든 코드가 여기에 모여 있습니다.

- `Post`: 게시글 엔티티로, DB 테이블과 1:1로 매핑됩니다.
- `PostRepository`: JPA를 이용해 게시글 데이터를 조회/저장합니다.
- `PostService`: 비즈니스 로직(검증, 수정 흐름 등)을 담당합니다.
- `PostController`: REST API 엔드포인트를 정의합니다.
- `PostRequest`/`PostResponse`: 요청과 응답에 사용하는 DTO입니다.
- `PostNotFoundException`: 존재하지 않는 게시글에 접근했을 때 사용할 사용자 정의 예외입니다.
- `PostDataInitializer`: 초기 실행 시 샘플 데이터를 넣어주는 유틸리티입니다.

이 패키지를 참고하면 다른 도메인 기능도 비슷한 형태로 확장할 수 있습니다.
