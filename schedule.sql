use `schedule-develop`;

# Lv 1
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

# Lv 2
# 멤버 테이블 삭제
DROP TABLE member;
# 멤버 테이블 생성
CREATE TABLE member (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        created_at TIMESTAMP NOT NULL,
        modified_at TIMESTAMP NOT NULL
);

# 일정 테이블 삭제
DROP TABLE schedule;
# 일정 테이블 생성
CREATE TABLE schedule (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      member_id BIGINT NOT NULL,
      title VARCHAR(255) NOT NULL,
      contents VARCHAR(255) NOT NULL,
      created_at TIMESTAMP NOT NULL,
      modified_at TIMESTAMP NOT NULL,
      CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);


