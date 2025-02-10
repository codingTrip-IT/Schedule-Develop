package com.example.scheduledevelop.domain.repository;

import com.example.scheduledevelop.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllBySchedule_Id(Long scheduleId);

    long countBySchedule_Id(Long scheduleId);

}
