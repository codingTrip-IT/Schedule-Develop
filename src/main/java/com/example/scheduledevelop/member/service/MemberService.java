package com.example.scheduledevelop.member.service;

import com.example.scheduledevelop.global.config.WebConfig;
import com.example.scheduledevelop.global.exception.ApiError;
import com.example.scheduledevelop.global.exception.ApplicationException;
import com.example.scheduledevelop.global.exception.CustomErrorMessageCode;
import com.example.scheduledevelop.global.exception.ErrorMessageCode;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.member.repository.MemberRepository;
import com.example.scheduledevelop.member.dto.SingUpResponseDto;
import com.example.scheduledevelop.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final WebConfig.PasswordEncoder passwordEncoder;

    @Transactional
    public SingUpResponseDto save(String name, String email, String password) {

        String encyptPassword = passwordEncoder.encode(password); //비밀번호 암호화

        Member member = new Member(name, email, encyptPassword);
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

//        Optional<Member> optionalMember = memberRepository.findById(id);
//
//        if (optionalMember.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 id가 없습니다."+id);
//        }
//        Member findMember = optionalMember.get();

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                     CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );

        return new MemberResponseDto(member.getId(),member.getName(),member.getEmail());
    }

    @Transactional
    public MemberResponseDto updateNameAndEmail(Long id, String name, String email, Member loginMember) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                     CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );

        // 본인만 수정 가능
        if (!loginMember.getId().equals(id)){
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                            CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }

//        Member findMember = memberRepository.findByIdOrElseThrow(id);
        member.updateNameAndEmail(name,email);
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }

    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword, Member loginMember) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                     CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );

        String DbPassword = member.getPassword();
        log.info("DbPassword={}", DbPassword);

        if (!passwordEncoder.matches(oldPassword,DbPassword)){
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
            throw new ApplicationException(ErrorMessageCode.UNAUTHORIZED,
                    List.of(new ApiError(CustomErrorMessageCode.INVALID_PASSWORD.getStatus(),
                            CustomErrorMessageCode.INVALID_PASSWORD.getMessage())));
        }

        String encyptPassword = passwordEncoder.encode(newPassword); //비밀번호 암호화
        log.info("encyptPassword={}", encyptPassword);

        member.updatePassword(encyptPassword);
    }

    @Transactional
    public void delete(Long id, Member loginMember) {

//        Member findMember = memberRepository.findByIdOrElseThrow(id);
//
//        if (!memberRepository.existsById(id)){
//            throw new IllegalArgumentException("해당 id가 존재하지 않습니다.");
//        }

        // 본인만 삭제 가능
        if (!loginMember.getId().equals(id)) {
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                                         CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }

        memberRepository.deleteById(id);
    }


}
