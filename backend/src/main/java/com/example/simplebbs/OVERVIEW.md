# com.example.simplebbs 패키지 개요

이 패키지는 애플리케이션 전반을 구성하는 최상위 코드들을 포함합니다.

- `SimpleBbsApplication`: Spring Boot 애플리케이션의 시작점입니다.
- `common/`: 예외 처리 등 여러 도메인에서 공용으로 쓰는 도구를 모아둡니다.
- `post/`: 게시글 CRUD 기능을 담당하는 도메인 패키지입니다.

새로운 도메인을 추가할 때는 `post/`와 같은 패키지를 하나 더 만드는 방식으로 확장할 수 있습니다.
