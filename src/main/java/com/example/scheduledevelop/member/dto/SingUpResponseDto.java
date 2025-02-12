package com.example.scheduledevelop.member.dto;

import lombok.Getter;

/**
 * MemberResponseDto : 회원 생성 응답 DTO
 * - 필드 : 회원 id, 회원명, 회원 이메일
 */
@Getter
public class SingUpResponseDto {

    private final Long id;
    private final String name;
    private final String email;

    public SingUpResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
