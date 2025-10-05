package com.example.simplebbs.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * 게시글(Post) 정보를 데이터베이스 테이블과 연결하기 위한 JPA 엔티티입니다.
 * "@Entity가 붙으면 JPA가 테이블로 관리한다" 정도만 알아도 이해할 수 있도록
 * 각 필드가 어떤 역할을 하는지 한국어로 상세히 설명합니다.
 */
@Entity
@Table(name = "posts")
public class Post {

    /**
     * 각 게시글을 구분하기 위한 기본키입니다.
     * IDENTITY 전략을 사용하면 데이터베이스가 알아서 증가하는 숫자를 채워줍니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시글 제목입니다. 150자를 넘지 않도록 DB 레벨에서도 제한을 걸어둡니다.
     */
    @Column(nullable = false, length = 150)
    private String title;

    /**
     * 게시글 내용입니다. 긴 글도 저장할 수 있도록 TEXT 타입을 사용합니다.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 글 작성자의 이름입니다. 운영자 분들이 흔히 "작성자"라고 부르는 값과 같습니다.
     */
    @Column(nullable = false, length = 50)
    private String author;

    /**
     * 글이 처음 작성된 시각입니다. prePersist 훅에서 값이 자동으로 채워집니다.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 글이 마지막으로 수정된 시각입니다. 새 글을 만들거나 수정할 때 자동으로 갱신됩니다.
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * JPA가 객체를 생성할 때 사용하기 위한 기본 생성자입니다. (직접 호출할 일은 거의 없습니다.)
     */
    public Post() {
    }

    /**
     * 코드에서 새 게시글을 만들 때 편하게 쓰기 위한 생성자입니다.
     */
    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /**
     * 엔티티가 처음 DB에 저장되기 직전에 자동으로 실행되는 메서드입니다.
     * createdAt과 updatedAt을 현재 시각으로 맞춰줍니다.
     */
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * 엔티티가 수정되어 저장되기 전에 updatedAt 값을 현재 시각으로 덮어씁니다.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /** 게시글 번호를 조회할 때 사용하는 getter입니다. */
    public Long getId() {
        return id;
    }

    /** 게시글 제목을 조회할 때 사용하는 getter입니다. */
    public String getTitle() {
        return title;
    }

    /** 게시글 제목을 변경할 때 사용하는 setter입니다. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 게시글 내용을 조회할 때 사용하는 getter입니다. */
    public String getContent() {
        return content;
    }

    /** 게시글 내용을 수정할 때 사용하는 setter입니다. */
    public void setContent(String content) {
        this.content = content;
    }

    /** 작성자 이름을 확인할 때 사용하는 getter입니다. */
    public String getAuthor() {
        return author;
    }

    /** 작성자 이름을 바꿀 때 사용하는 setter입니다. */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** 글이 언제 만들어졌는지 확인할 때 사용하는 getter입니다. */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** 글이 마지막으로 언제 수정됐는지 확인할 때 사용하는 getter입니다. */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
