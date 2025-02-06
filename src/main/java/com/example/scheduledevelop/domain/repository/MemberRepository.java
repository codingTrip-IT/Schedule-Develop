package com.example.scheduledevelop.domain.repository;

import com.example.scheduledevelop.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
