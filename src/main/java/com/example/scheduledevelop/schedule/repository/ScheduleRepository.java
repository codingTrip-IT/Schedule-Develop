package com.example.scheduledevelop.schedule.repository;

import com.example.scheduledevelop.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ScheduleRepository : 회원 레포지토리(JpaRepository 상속받음)
 * - findAllByOrderByModifiedAtDesc : 일정 목록 조회(수정일로 내림차순 정렬)
 */
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    Page<Schedule> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
