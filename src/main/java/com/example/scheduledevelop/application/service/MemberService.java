package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.repository.MemberRepository;
import com.example.scheduledevelop.presentation.dto.CreateMemberResponseDto;
import com.example.scheduledevelop.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {

        return memberRepository.findAll()
                .stream()
                .map(MemberResponseDto::toDto)
                .toList();
    }


//
//    @Transactional(readOnly = true)
//
//    @Transactional
//    @Transactional
}
