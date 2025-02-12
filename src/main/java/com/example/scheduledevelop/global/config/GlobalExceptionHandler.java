package com.example.scheduledevelop.global.config;

import com.example.scheduledevelop.global.exception.ApplicationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *  Global Exception Handler
 * - @RestControllerAdvice : 모든 컨트롤러에서 발생하는 예외를 전역에서 처리
 * - @ExceptionHandler : 특정 예외를 잡아 커스텀 응답 반환
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ApplicationException 예외처리 핸들러
     * - 서비스 계층에서 발생한 ApplicationException을 처리하고, JSON 응답을 반환
     * @param ex : 발생한 ApplicationException 객체
     * @return : ResponseEntity<Map<String, Object>> 형태의 JSON 응답
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handleApplicationException(ApplicationException ex) {
        // 클라이언트에게 반환할 오류 응답 객체 생성
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", ex.getErrorMessageCode().getCode());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("errors", ex.getErrors());
        errorResponse.put("timestamp", LocalDateTime.now());

        // 에러 코드에 따라 적절한 HTTP 상태 코드 설정
        HttpStatus status = switch (ex.getErrorMessageCode()) {
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST; // 400: 잘못된 요청
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED; // 401: 인증 실패
            case FORBIDDEN -> HttpStatus.FORBIDDEN; // 403: 접근 권한 없음
            case NOT_FOUND -> HttpStatus.NOT_FOUND; // 404: 리소스를 찾을 수 없음
            case INTERNAL_SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR; // 500: 서버 내부 오류
            default -> HttpStatus.INTERNAL_SERVER_ERROR; // 기본값: 500
        };
        // HTTP 상태 코드와 함께 JSON 응답 반환
        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * ValidationException 예외처리 핸들러
     * - Spring의 @Valid 또는 @Validated 어노테이션을 사용한 요청 데이터 검증에서 발생하는 예외를 처리
     * - 예: DTO의 필드 값이 유효성 검사(@NotBlank, @Size 등)를 통과하지 못한 경우 발생
     * @param ex MethodArgumentNotValidException 예외 객체 (검증 실패 정보 포함)
     * @return HTTP 400 BAD_REQUEST 응답과 함께 첫 번째 검증 실패 메시지를 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String firstErrorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElseThrow(() -> new IllegalStateException("검증 에러가 반드시 존재해야 합니다."));

        return getErrorResponse(HttpStatus.BAD_REQUEST, firstErrorMessage);
    }

    /**
     * 에러 응답을 생성하는 메서드
     * @param status HTTP 상태 코드
     * @param message 클라이언트에 전달할 에러 메시지
     * @return ResponseEntity<Map<String, Object>> 형식의 에러 응답
     */
    private ResponseEntity<Map<String, Object>> getErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.name());
        errorResponse.put("code", status.value());
        errorResponse.put("message", message);

        return new ResponseEntity<>(errorResponse, status);
    }
}
