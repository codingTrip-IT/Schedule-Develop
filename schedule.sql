use `schedule-develop`;

# 멤버 테이블 삭제
DROP TABLE member;
# 멤버 테이블 생성
CREATE TABLE member (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        created_at DATETIME NOT NULL,
        modified_at DATETIME NOT NULL
);

# 일정 테이블 삭제
DROP TABLE schedule;
# 일정 테이블 생성
CREATE TABLE schedule (
          id BIGINT AUTO_INCREMENT PRIMARY KEY,
          member_id BIGINT NOT NULL,
          title VARCHAR(255) NOT NULL,
          contents VARCHAR(255) NOT NULL,
          created_at DATETIME NOT NULL,
          modified_at DATETIME NOT NULL,
          CONSTRAINT fk_member_s FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);

# 댓글 테이블 삭제
DROP TABLE comment;
# 댓글 테이블 생성
CREATE TABLE comment (
          id BIGINT AUTO_INCREMENT PRIMARY KEY,
          member_id BIGINT NOT NULL,
          schedule_id BIGINT NOT NULL,
          contents VARCHAR(255) NOT NULL,
          created_at DATETIME NOT NULL,
          modified_at DATETIME NOT NULL,
          CONSTRAINT fk_member_c FOREIGN KEY (member_id) REFERENCES MEMBER (id),
          CONSTRAINT fk_schedule FOREIGN KEY (schedule_id) REFERENCES SCHEDULE (id)
);


