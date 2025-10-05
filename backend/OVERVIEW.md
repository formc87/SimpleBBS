# backend 폴더 개요

이 폴더는 Spring Boot 기반 REST API 서버의 모든 소스를 담고 있습니다.

## 주요 구성

- `pom.xml`: Maven 빌드 설정과 라이브러리 의존성을 정의합니다.
- `src/main/java/`: 애플리케이션 자바 코드가 위치합니다.
- `src/main/resources/`: 애플리케이션 설정 파일과 정적 리소스를 관리합니다.

## 코드 구조

```
src/main/java
└── com/example/simplebbs
    ├── SimpleBbsApplication.java   # 애플리케이션 시작점
    ├── common/                     # 전역 예외 처리 등 공통 구성요소
    └── post/                       # 게시글 도메인의 컨트롤러/서비스/엔티티
```

애플리케이션은 게시글(Post) 기능에 집중하도록 구성되어 있어, 흐름을 따라가며 살펴보기 쉽습니다.
