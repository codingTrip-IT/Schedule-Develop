package com.example.scheduledevelop.member.dto;

import com.example.scheduledevelop.member.entity.Member;
import lombok.Getter;

/**
 * MemberResponseDto : 회원 응답 DTO
 * - 필드 : 회원 id, 회원명, 회원 이메일
 */
@Getter
public class MemberResponseDto {

    private final Long id;
    private final String name;
    private final String email;

    public MemberResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * dto로 변환하는 메서드
     * @Param 회원 id, 회원명, 회원 이메일
     */
    public static MemberResponseDto toDto(Member member){
        return new MemberResponseDto(member.getId(),member.getName(),member.getEmail());
    }
}
