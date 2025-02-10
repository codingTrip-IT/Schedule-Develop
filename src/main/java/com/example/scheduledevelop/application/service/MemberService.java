package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.config.WebConfig;
import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.repository.MemberRepository;
import com.example.scheduledevelop.presentation.dto.SingUpResponseDto;
import com.example.scheduledevelop.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final WebConfig.PasswordEncoder passwordEncoder;

    @Transactional
    public SingUpResponseDto save(String name, String email, String password) {

        Member member = new Member(name, email, password);

        Member savedMember = memberRepository.save(member);

        return new SingUpResponseDto(savedMember.getId(),savedMember.getName(),savedMember.getEmail());
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

    @Transactional
    public void updateNameAndEmail(Long id, String name, String email) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        findMember.updateNameAndEmail(name,email);
    }

    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);
        String DbPassword = findMember.getPassword();
        log.info("DbPassword={}", DbPassword);

        if (!passwordEncoder.matches(oldPassword,DbPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }

        String encyptPassword = passwordEncoder.encode(newPassword); //비밀번호 암호화
        log.info("encyptPassword={}", encyptPassword);

        findMember.updatePassword(encyptPassword);
    }

    @Transactional
    public void delete(Long id) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        memberRepository.delete(findMember);
    }


}
