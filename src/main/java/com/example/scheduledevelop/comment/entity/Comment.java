package com.example.scheduledevelop.comment.entity;

import com.example.scheduledevelop.global.entity.BaseEntity;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comment : 댓글 엔티티(BaseEntity 상속받음)
 * - 필드 : 댓글 id, 댓글 내용, 회원과 연관관계, 일정과 연관관계
 */
@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(String contents, Schedule schedule, Member member) {
        this.contents = contents;
        this.schedule = schedule;
        this.member = member;
    }

    /**
     * 댓글 내용 수정
     * @param contents 댓글 내용
     */
    public void updateContents(String contents) {
        this.contents = contents;
    }
}
