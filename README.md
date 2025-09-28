# Spring Boot + React 게시판 프로젝트

이 프로젝트는 Spring Boot를 백엔드로, React.js를 프론트엔드로 사용하는 간단한 게시판 웹 애플리케이션입니다.

---

## 프로젝트 개요

- 백엔드: Spring Boot (Java 기반 REST API)
- 프론트엔드: React.js (최신 버전)
- 데이터베이스: H2 (인메모리, 테스트용)
- 주요 기능: 게시글 CRUD (생성, 조회, 수정, 삭제)

---

## 개발 환경

- JDK 17 이상
- Node.js 18 이상
- Maven 3.x
- React 18.x

---

## 프로젝트 구조

/backend # Spring Boot 애플리케이션 소스 및 설정
/frontend # React.js 프론트엔드 소스 및 설정



---

## 백엔드 실행 방법

1. `/backend` 디렉토리로 이동
2. `./mvnw spring-boot:run` 명령어로 실행

---

## 프론트엔드 실행 방법

1. `/frontend` 디렉토리로 이동
2. `npm install` 명령어로 의존성 설치
3. `npm start` 명령어로 개발 서버 실행

---

## 주요 API 목록

| 메서드 | 경로            | 설명               |
|--------|-----------------|--------------------|
| GET    | /api/posts      | 게시글 목록 조회    |
| POST   | /api/posts      | 게시글 작성        |
| GET    | /api/posts/{id} | 특정 게시글 조회    |
| PUT    | /api/posts/{id} | 게시글 수정        |
| DELETE | /api/posts/{id} | 게시글 삭제        |

---

## 기능 설명

- 게시글 생성, 조회, 수정, 삭제 기능
- React에서 REST API 호출하여 UI에 반영
- 간단한 유효성 검사 및 에러 핸들링 포함

---

## 참고 사항

- 백엔드와 프론트엔드 간 CORS 설정 필요
- 데이터베이스는 테스트 용도로 H2 인메모리 DB 사용
- 실제 배포 시 MySQL 등 외부 DB로 변경 권장
- 인증 및 권한 관리는 추후 확장 예정

---

## 프로젝트 시작 전 준비 사항

1. JDK, Node.js, Maven 설치 확인
2. IDE로 백엔드와 프론트엔드 프로젝트 열기
3. `backend/src/main/resources/application.properties`에서 DB 설정 확인 또는 변경

---

## 기타

이 프로젝트는 AI 도우미 Codex 같은 도구와 함께 개발하면 더 빠르고 효율적으로 기능을 구현할 수 있습니다.

---

즐거운 개발 되세요!
