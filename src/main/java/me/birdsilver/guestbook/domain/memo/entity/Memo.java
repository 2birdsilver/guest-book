package me.birdsilver.guestbook.domain.memo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Memo {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 작성자
    @Column(name = "writer", nullable = false)
    private String writer;

    // 받는자
    @Column(name = "recipient", nullable = false)
    private String recipient;

    // 내용
    @Column(name = "content", nullable = false)
    private String content;

    // 작성일
    @Column(name = "date", nullable = false)
    private LocalDate date;

    // 비밀번호
    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public Memo(String writer, String recipient, String content, Date date, String password) {
        this.writer = writer;
        this.recipient = recipient;
        this.content = content;
        this.date = LocalDate.now();
        this.password = password;
    }

    public void update(String writer, String content, Date date) {
        this.writer = writer;
        this.content = content;
        //수정일 추가?
    }
}
