package com.example.scheduledevelop.domain.repository;

import com.example.scheduledevelop.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface MemberRepository extends JpaRepository<Member,Long> {

    default Member findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 id가 존재하지 않습니다."+id)
        );
    }
}
