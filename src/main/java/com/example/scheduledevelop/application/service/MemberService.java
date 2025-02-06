package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.repository.MemberRepository;
import com.example.scheduledevelop.presentation.dto.CreateMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public CreateMemberResponseDto save(String name, String email) {

        Member member = new Member(name, email);

        Member savedMember = memberRepository.save(member);

        return new CreateMemberResponseDto(savedMember.getId(),savedMember.getName(),savedMember.getEmail());
    }

//    @Transactional(readOnly = true)
//
//    @Transactional(readOnly = true)
//
//    @Transactional
//    @Transactional
}
