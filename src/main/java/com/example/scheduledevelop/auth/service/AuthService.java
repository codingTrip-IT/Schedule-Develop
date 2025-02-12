package com.example.scheduledevelop.auth.service;

import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthService : 로그인 서비스
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    /**
     * 로그인
     * @param email 이메일
     * @return 이메일에 해당하는 멤버 반환
     */
    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    /**
     * 로그인
     * @param email 이메일
     * @param password 비밀번호
     * @return 이메일과 비밀번호에 해당하는 멤버 반환
     */
    @Transactional(readOnly = true)
    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }
}
