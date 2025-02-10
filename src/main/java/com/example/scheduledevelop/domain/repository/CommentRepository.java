package com.example.scheduledevelop.domain.repository;

import com.example.scheduledevelop.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
