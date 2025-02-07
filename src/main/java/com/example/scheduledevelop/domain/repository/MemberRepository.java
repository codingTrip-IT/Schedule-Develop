package com.example.scheduledevelop.domain.repository;

import com.example.scheduledevelop.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findMemberByEmail(String email);

    default Member findMemberByEmailOrElseThrow(String email){
        return findMemberByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"email을 찾지 못했습니다. = "+email)
        );
    }

    default Member findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 id가 존재하지 않습니다. = "+id)
        );
    }
}
