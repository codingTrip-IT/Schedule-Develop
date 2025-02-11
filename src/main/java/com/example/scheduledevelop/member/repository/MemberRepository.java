package com.example.scheduledevelop.member.repository;

import com.example.scheduledevelop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

    Member findByEmailAndPassword(String email, String password);

//    default Member findByIdOrElseThrow(Long id){
//        return findById(id).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 id가 존재하지 않습니다. = "+id)
//        );
//    }
}
