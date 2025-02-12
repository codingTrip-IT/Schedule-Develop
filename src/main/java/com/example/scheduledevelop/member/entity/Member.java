package com.example.scheduledevelop.member.entity;

import com.example.scheduledevelop.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Member : 회원 엔티티(BaseEntity 상속받음)
 * - 필드 : 회원 id, 회원명, 이메일, 비밀번호
 */
@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void updateNameAndEmail(String name,String email) {
        this.name = name;
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password ;
    }
}
