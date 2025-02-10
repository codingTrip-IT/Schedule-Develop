package com.example.scheduledevelop.domain.repository;

import com.example.scheduledevelop.domain.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    Page<Schedule> findAllByOrderByModifiedAtDesc(Pageable pageable);

    default Schedule findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 id가 존재하지 않습니다."+id));
    }
}
