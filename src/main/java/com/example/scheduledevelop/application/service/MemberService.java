package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.repository.MemberRepository;
import com.example.scheduledevelop.presentation.dto.CreateMemberResponseDto;
import com.example.scheduledevelop.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 id가 없습니다."+id);
        }

        Member findMember = optionalMember.get();

        return new MemberResponseDto(findMember.getId(),findMember.getName(),findMember.getEmail());
    }


//    @Transactional
//    @Transactional
}
