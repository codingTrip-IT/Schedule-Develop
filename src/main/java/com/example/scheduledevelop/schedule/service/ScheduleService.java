package com.example.scheduledevelop.schedule.service;

import com.example.scheduledevelop.global.exception.ApiError;
import com.example.scheduledevelop.global.exception.ApplicationException;
import com.example.scheduledevelop.global.exception.CustomErrorMessageCode;
import com.example.scheduledevelop.global.exception.ErrorMessageCode;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.schedule.entity.Schedule;
import com.example.scheduledevelop.comment.repository.CommentRepository;
import com.example.scheduledevelop.schedule.repository.ScheduleRepository;
import com.example.scheduledevelop.global.page.CustomResponsePage;
import com.example.scheduledevelop.schedule.dto.FindAllScheduleResponseDto;
import com.example.scheduledevelop.schedule.dto.ScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ScheduleService : 일정 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    /**
     * 일정 생성
     * @param title 일정 제목
     * @param contents 일정 내용
     * @param loginMember 세션 로그인 멤버
     * @return ScheduleResponseDto 일정 응답 DTO 반환
     */
    @Transactional
    public ScheduleResponseDto save(String title, String contents, Member loginMember) {

        Schedule schedule = new Schedule(title, contents, loginMember);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getMember().getName(),
                savedSchedule.getMember().getEmail(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    /**
     * 일정 목록 조회
     * @param page 페이지 번호(기본값 0)
     * @param size 페이지 크기(기본값 10)
     * @return CustomResponsePage<FindAllScheduleResponseDto> 응답 DTO 반환
     */
    @Transactional(readOnly = true)
    public CustomResponsePage<FindAllScheduleResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> schedulePage = scheduleRepository.findAllByOrderByModifiedAtDesc(pageable);
        Page<FindAllScheduleResponseDto> dtoPage = schedulePage.map(
                schedule -> {
                    Member member = schedule.getMember();
                    long countComment = commentRepository.countBySchedule_Id(schedule.getId());

                    return new FindAllScheduleResponseDto(
                            schedule.getId(),
                            schedule.getTitle(),
                            schedule.getContents(),
                            countComment,           //댓글 개수
                            schedule.getCreatedAt(),
                            schedule.getModifiedAt(),
                            member.getName()
                    );
                });
        return new CustomResponsePage<>(dtoPage);
    }

    /**
     * 일정 선택 조회
     * @param id 일정 id
     * getScheduleByIdOrElseThrow : 일정 id로 일정 조회
     * @return ScheduleResponseDto 일정 응답 DTO 반환
     */
    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = getScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getMember().getName(),
                schedule.getMember().getEmail(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    /**
     * 일정 선택 수정(제목,내용 수정)
     * @param id 일정 id
     * @param title 일정 제목
     * @param contents 일정 내용
     * @param loginMember 세션 로그인 멤버
     * getScheduleByIdOrElseThrow : 일정 id로 일정 조회
     * validateMemberId : 본인(작성자) 검증 로직
     */
    @Transactional
    public ScheduleResponseDto updateTitleContents(Long id, String title, String contents, Member loginMember) {
        Schedule schedule = getScheduleByIdOrElseThrow(id);
        validateMemberId(loginMember, schedule);

        schedule.updateTitleContents(title, contents);
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getMember().getName(),
                schedule.getMember().getEmail(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    /**
     * 일정 선택 삭제
     * @param id 일정 id
     * @param loginMember 세션 로그인 멤버
     * getScheduleByIdOrElseThrow : 일정 id로 일정 조회
     * validateMemberId : 본인(작성자) 검증 로직
     */
    @Transactional
    public void delete(Long id, Member loginMember) {
        Schedule schedule = getScheduleByIdOrElseThrow(id);
        validateMemberId(loginMember, schedule);
        scheduleRepository.deleteById(id);
    }

    /**
     * 일정 id로 일정 조회
     * @param id 일정 id
     * @exception ApplicationException 해당 id를 찾을 수 없는 경우, 404 예외처리(커스텀 예외처리 ID_NOT_FOUND)
     * @return 일정 id에 해당하는 일정 반환
     */
    private Schedule getScheduleByIdOrElseThrow(Long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );
    }

    /**
     * 본인(작성자)만 본인(작성자) 검증 로직
     * @param loginMember 세션 로그인 멤버
     * @param schedule 일정
     * loginMember(로그인 정보)의 id와 DB에서 조회한 id 비교
     * @exception ApplicationException 각각의 id가 불일치할 경우 403 예외처리(커스텀 예외처리 NOT_OWNER)
     */
    private static void validateMemberId(Member loginMember, Schedule schedule) {
        Long findMemberId = schedule.getMember().getId();
        log.info("fineMemberId={}",findMemberId);
        log.info("loginMember.getId()={}", loginMember.getId());

        if (loginMember.getId() != findMemberId){
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                            CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }
    }
}
