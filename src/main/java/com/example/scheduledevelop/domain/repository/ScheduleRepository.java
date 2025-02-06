package com.example.scheduledevelop.domain.repository;

import com.example.scheduledevelop.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

}
