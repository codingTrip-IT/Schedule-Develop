package com.example.scheduledevelop.member.repository;

import com.example.scheduledevelop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MemberRepository : 회원 레포지토리(JpaRepository 상속받음)
 * - findByEmail : 이메일로 회원 조회
 * - findByEmailAndPassword : 이메일, 비밀번호로 회원 조회
 */
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

    Member findByEmailAndPassword(String email, String password);
}
