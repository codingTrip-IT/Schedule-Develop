package com.example.scheduledevelop.schedule.entity;

import com.example.scheduledevelop.global.entity.BaseEntity;
import com.example.scheduledevelop.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Schedule : 일정 엔티티(BaseEntity 상속받음)
 * - 필드 : 일정 id, 일정 제목, 일정 내용, 회원과 연관관계
 */
@Getter
@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Schedule(String title, String contents, Member member) {
        this.title = title;
        this.contents = contents;
        this.member = member;
    }

    /**
     * 일정 제목, 내용 수정
     * @param title 일정 제목
     * @param contents 일정 내용
     */
    public void updateTitleContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
