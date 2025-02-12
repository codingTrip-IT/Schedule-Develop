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

/**
 * MemberService : 회원 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final WebConfig.PasswordEncoder passwordEncoder;

    /**
     * 회원 생성(회원가입)
     * @param name 회원명
     * @param email 이메일
     * @param password 비밀번호
     * encryptPassword : 비밀번호 암호화 후 저장
     * @return SingUpResponseDto 회원 생성 응답 DTO 반환
     */
    @Transactional
    public SingUpResponseDto save(String name, String email, String password) {

        String encryptPassword = passwordEncoder.encode(password); //비밀번호 암호화

        Member member = new Member(name, email, encryptPassword);
        Member savedMember = memberRepository.save(member);

        return new SingUpResponseDto(savedMember.getId(),savedMember.getName(),savedMember.getEmail());
    }

    /**
     * 회원 목록 조회
     * @return MemberResponseDto 회원 응답 DTO 리스트 반환
     */
    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponseDto::toDto)
                .toList();
    }

    /**
     * 회원 선택 조회
     * @param id 회원 id
     * getMemberByIdOrElseThrow : 회원 id로 회원 조회
     * @return MemberResponseDto 회원 생성 응답 DTO 반환
     */
    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member member = getMemberByIdOrElseThrow(id);
        return new MemberResponseDto(member.getId(),member.getName(),member.getEmail());
    }

    /**
     * 회원 선택 수정(회원명, 이메일 수정)
     * @param id 회원 id
     * @param name 회원명
     * @param email 이메일
     * @param loginMember 세션 로그인 멤버
     * getMemberByIdOrElseThrow : 회원 id로 회원 조회
     * validateMemberId : 본인(작성자) 검증 로직
     * @return MemberResponseDto 회원 응답 DTO 반환
     */
    @Transactional
    public MemberResponseDto updateNameAndEmail(Long id, String name, String email, Member loginMember) {
        Member member = getMemberByIdOrElseThrow(id);
        validateMemberId(id, loginMember);
        member.updateNameAndEmail(name,email);
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }

    //todo 리팩토링 하기
    /**
     * 회원 선택 수정(비밀번호 수정)
     * @param id 회원 id
     * @param oldPassword 수정 전 비밀번호
     * @param newPassword 수정 후 비밀번호
     * @param loginMember 세션 로그인 멤버
     * getMemberByIdOrElseThrow : 회원 id로 회원 조회
     */
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword, Member loginMember) {
        Member member = getMemberByIdOrElseThrow(id);

        String DbPassword = member.getPassword();
        log.info("DbPassword={}", DbPassword);

        if (!passwordEncoder.matches(oldPassword,DbPassword)){
            throw new ApplicationException(ErrorMessageCode.UNAUTHORIZED,
                    List.of(new ApiError(CustomErrorMessageCode.INVALID_PASSWORD.getStatus(),
                            CustomErrorMessageCode.INVALID_PASSWORD.getMessage())));
        }

        String encryptPassword = passwordEncoder.encode(newPassword); //비밀번호 암호화
        log.info("encryptPassword={}", encryptPassword);

        member.updatePassword(encryptPassword);
    }

    /**
     * 회원 선택 삭제
     * @param id 회원 id
     * @param loginMember 세션 로그인 멤버
     * getMemberByIdOrElseThrow : 회원 id로 회원 조회
     * validateMemberId : 회원 id로 회원 조회
     */
    @Transactional
    public void delete(Long id, Member loginMember) {
        getMemberByIdOrElseThrow(id);
        validateMemberId(id, loginMember);
        memberRepository.deleteById(id);
    }

    /**
     * 회원 id로 회원 조회
     * @param id 회원 id
     * @exception ApplicationException 해당 id를 찾을 수 없는 경우, 404 예외처리(커스텀 예외처리 ID_NOT_FOUND)
     * @return 회원 id에 해당하는 회원 반환
     */
    private Member getMemberByIdOrElseThrow(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );
    }

    /**
     * 본인(작성자)만 본인(작성자) 검증 로직
     * @param id 회원 id
     * @param loginMember 세션 로그인 멤버
     * loginMember(로그인 정보)의 id와 DB에서 조회한 id 비교
     * @exception ApplicationException 각각의 id가 불일치할 경우 403 예외처리(커스텀 예외처리 NOT_OWNER)
     */
    private static void validateMemberId(Long id, Member loginMember) {
        if (!loginMember.getId().equals(id)){
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                            CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }
    }


}
