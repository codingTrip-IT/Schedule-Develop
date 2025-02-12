package com.example.scheduledevelop.comment.repository;

import com.example.scheduledevelop.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CommentRepository : 댓글 레포지토리(JpaRepository 상속받음)
 * - findAllBySchedule_Id : 일정 id로 댓글 조회
 * - countBySchedule_Id : 일정 idf로 댓글 개수 조회
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllBySchedule_Id(Long scheduleId);

    long countBySchedule_Id(Long scheduleId);

}
