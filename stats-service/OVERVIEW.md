# stats-service 폴더 개요

이 디렉터리는 게시판 서비스와 분리된 Kafka 소비자 기반 통계 마이크로서비스를 담고 있습니다. 루트에 있는 `docker-compose.kafka.yml`을 실행하면 Kafka 브로커와 함께 Kafka UI도 기동되어 이벤트 흐름을 확인할 수 있습니다.

## 주요 구성

- `pom.xml`: Spring Boot 기반 Kafka Consumer 애플리케이션 설정
- `src/main/java/com/example/poststats/`: 애플리케이션 진입점과 통계 로직, REST API를 포함합니다.
- `src/main/resources/application.properties`: 기본 포트(8081)와 Kafka 접속 정보, 토픽 이름을 관리합니다.

## 동작 개요

1. 게시판 백엔드에서 게시글 생성/수정/삭제 시 Kafka 토픽(`post-events`)으로 이벤트를 발행합니다.
2. 통계 서비스는 해당 토픽을 소비하여 이벤트 유형별 카운트와 게시글 스냅샷을 실시간으로 갱신합니다.
3. REST API `GET /api/stats`를 통해 누적 통계와 최신 게시글 정보를 제공합니다.

Kafka 브로커 주소는 `KAFKA_BOOTSTRAP_SERVERS` 환경 변수로 주입할 수 있으며, 필요 시 `SERVER_PORT`로 HTTP 포트도 조정 가능합니다.
