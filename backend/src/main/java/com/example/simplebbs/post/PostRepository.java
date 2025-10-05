package com.example.simplebbs.post;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Post 엔티티를 DB와 연결해 주는 JPA 저장소 인터페이스입니다.
 * JpaRepository를 상속하면 기본적인 CRUD(등록/조회/수정/삭제) 메서드가 자동으로 생깁니다.
 * 따로 구현 클래스를 만들 필요가 없어서 운영자 입장에서도 관리가 편합니다.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
