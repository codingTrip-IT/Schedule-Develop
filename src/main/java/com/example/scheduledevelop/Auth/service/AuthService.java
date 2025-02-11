package com.example.scheduledevelop.Auth.service;

import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
            return memberRepository.findByEmail(email);
    }
}
