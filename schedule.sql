use `schedule-develop`;

# 일정 테이블 삭제
DROP TABLE schedule;

# 일정 테이블 생성
CREATE TABLE schedule (
          id BIGINT AUTO_INCREMENT PRIMARY KEY,
          username VARCHAR(255) NOT NULL,
          title VARCHAR(255) NOT NULL,
          contents VARCHAR(255) NOT NULL,
          created_at TIMESTAMP NOT NULL,
          modified_at TIMESTAMP NOT NULL
);

select * from schedule;