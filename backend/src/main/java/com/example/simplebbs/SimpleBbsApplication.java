package com.example.simplebbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션의 시작점입니다.
 * <p>
 * "main 메서드가 있는 클래스가 프로젝트의 엔트리"라고만 알고 있어도 괜찮도록
 * 이 클래스가 전체 서버를 부팅해 주는 엔진이라는 점을 강조하는 주석입니다.
 */
@SpringBootApplication
public class SimpleBbsApplication {

    /**
     * 자바 애플리케이션이 실행되면 가장 먼저 호출되는 메서드입니다.
     * SpringApplication.run(...)을 호출하면 내장 톰캣 서버가 켜지고
     * 우리가 작성한 컨트롤러/서비스 등이 자동으로 준비됩니다.
     */
    public static void main(String[] args) {
        SpringApplication.run(SimpleBbsApplication.class, args);
    }
}
