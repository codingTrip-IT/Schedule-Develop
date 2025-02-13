## 👉🏼 일정관리(Schedule) Develop 개인과제

### 🙋‍♀️ 일정관리(Schedule) Develop 과제 설명
- **일정관리(Schedule) Develop 과제**는 <u>Spring을 활용하여 일정 관리 앱을 Develop한 버전으로 구현</u>한 과제입니다.
- **작성자 : 조예인**


## 🛠 목차

1. [👩🏻‍ API 명세](#-API-명세)
2. [👩 ERD](#-ERD)
3. [👩🏻‍🏫 레벨별 설명](#-레벨별-설명)
    - [Lv 1](#Lv-1)
    - [Lv 2](#Lv-2)
    - [Lv 3](#Lv-3)
    - [Lv 4](#Lv-4)
    - [Lv 5](#Lv-5)
    - [Lv 6](#Lv-6)
    - [Lv 7](#Lv-7)
    - [Lv 8](#Lv-8)

4. [💥 한계점](#-한계점)
5. [📚 STACKS](#-STACKS)
6. [📞 Contact](#-Contact)

<br>   

## 👩🏻‍ API 명세
### 회원 API
 기능               | Method | URL             | Request                | Response | 상태코드    
|------------------|--------|-----------------|------------------------|----------|---------|
| 회원 생성(회원 가입)     | POST   | /members/signup | 요청 body                | 등록 정보    | 201 CREATED |
| 전체 회원 조회         | GET    | /members          | -                      | 다건 응답 정보 | 200: OK |
| 선택 회원 조회         | GET    | /members/{memberId} | path memberId          | 단건 응답 정보 | 200: OK |
| 선택 회원 <br/>회원명,이메일 수정 | PATCH    | /members/{memberId} | path memberId, 요청 body | 수정 정보    | 200: OK |
| 선택 회원 <br/>비밀번호 수정    | PATCH    | /members/{memberId}/password | path memberId, 요청 body | -        | 200: OK |
| 선택 회원 삭제         | DELETE    | /members/{memberId} | path memberId          | -        | 200: OK |


### 일정 API
 기능           | Method | URL                     | Request                  | Response | 상태코드       
|--------------|--------|-------------------------|--------------------------|----------|------------|
| 일정 생성        | POST   | /schedules              | 요청 body                  | 등록 정보    | 201 CREATED |
| 전체 일정 조회     | GET    | /schedules              | 요청 param(페이지 번호, 크기)     | 다건 응답 정보 | 200: OK  |
| 선택 일정 조회     | GET    | /schedules/{scheduleId} | path scheduleId          | 단건 응답 정보 | 200: OK  |
| 선택 일정 수정     | PATCH  | /schedules/{scheduleId} | path scheduleId, 요청 body | 수정 정보    | 200: OK  |
| 선택 일정 삭제     | DELETE | /schedules/{scheduleId} | path scheduleId          | -        | 200: OK   |


### 로그인/로그아웃 API
 기능   | Method | URL     | Request | Response | 상태코드         
|------|--------|---------|---------|----------|--------------|
| 로그인  | POST   | /login  | 요청 body | -        | 200: OK      |
| 로그아웃 | POST    | /logout | -       | -        | 200: OK |

### 댓글 API
 기능              | Method | URL                     | Request    | Response | 상태코드      
|-----------------|--------|-------------------------|------------|----------|-----------|
| 댓글 생성(선택 일정)    | POST   | /schedules/{scheduleId}/comments  | path scheduleId, 요청 body | 등록 정보    | 201 CREATED |
| 전체 댓글 조회(선택 일정) | GET    | /schedules/{scheduleId}/comments              | path scheduleId | 다건 응답 정보 | 200: OK |
| 선택 댓글 조회        | GET    | /schedules/comments/{commentId} | path commentId | 단건 응답 정보 | 200: OK |
| 선택 댓글 수정        | PATCH  | /schedules/comments/{commentId} | path commentId, 요청 body | 수정 정보    | 200: OK |
| 선택 댓글 삭제        | DELETE | /schedules/comments/{commentId} | path commentId | -        | 200: OK |

## 👩 ERD
<img width="707" alt="Image" src="https://github.com/user-attachments/assets/0cdc04aa-b139-4726-97c1-d82f12201d1f" />

## 👩🏻‍🏫 레벨별 설명
### Lv 1
### 일정 CRUD
- 일정 생성
- 실행 예시
```json 
POST : localhost:8080/schedules
        
요청 body :
{
    "username" : "study",
    "title" : "JPA",
    "contents" : "JPA 공부하기"
}
        
응답 body :
{
    "id" : 1,
    "username" : "study",
    "title" : "JPA",
    "contents" : "JPA 공부하기"
}
```
- 전체 일정 조회
- 실행 예시
```json 
GET : localhost:8080/schedules

응답 body :
[{
    "id": 2,
    "title": "JPA2",
    "contents": "JPA 공부하기2",
    "createdAt": "2025-02-12T14:36:44.604457",
    "modifiedAt": "2025-02-12T14:36:44.604457",
    "username": "study"
},
{
    "id": 1,
    "title": "JPA",
    "contents": "JPA 공부하기",
    "createdAt": "2025-02-12T14:36:35.976473",
    "modifiedAt": "2025-02-12T14:36:35.976473",
    "username": "study"
}]
```

- 선택 일정 조회
- 실행 예시
```json 
GET : localhost:8080/schedules/1
        
응답 body :
{
    "id": 1,
    "title": "JPA",
    "contents": "JPA 공부하기",
    "username": "study",
    "createdAt": "2025-02-12T14:36:35.976473",
    "modifiedAt": "2025-02-12T14:36:35.976473"
}
```
- 선택한 일정 수정(제목, 내용만 수정)
- 실행 예시
```json 
PATCH : localhost:8080/schedules/1
        
요청 body :
{
    "title": "스프링",
    "contents":"스케줄 생성 과제하기"
}

응답 body :
{
    "id": 1,
    "title": "스프링",
    "contents": "스케줄 생성 과제하기",
    "username" : "study"
    "createdAt": "2025-02-12T14:36:35.976473",
    "modifiedAt": "2025-02-12T14:36:35.976473"
}
```
- 선택한 일정 삭제
- 실행 예시
```json 
DELETE : localhost:8080/schedules/1

응답 : 200 OK
```

### Lv 2
### 유저 CRUD
- 유저 생성
- 실행 예시
```json 
POST : localhost:8080/members/signup
        
요청 body :
{
    "name" : "조예인",
    "email" : "codingtrip@naver.com"
}
        
응답 body :
{
    "id": 1,
    "name": "조예인",
    "email": "codingtrip@naver.com"
}
```
- 전체 유저 조회
- 실행 예시
```json 
GET : localhost:8080/members
        
응답 body :
[
    {
        "id": 1,
        "name": "조예인",
        "email": "codingtrip@naver.com"
    },
    {
        "id": 2,
        "name": "study",
        "email": "study@naver.com"
    }
]
```

- 선택 유저 조회
- 실행 예시
```json 
GET : localhost:8080/members/1
        
응답 body :
{
    "id": 1,
    "name": "조예인",
    "email": "codingtrip@naver.com"
}
```
- 선택한 유저 수정(회원명, 이메일)
- 실행 예시
```json 
PATCH : localhost:8080/members/1
        
요청 body :
{
"name" : "jo",
"email" : "jo@naver.com"
}

응답 body :
{
    "id": 1,
    "name": "jo",
    "email": "jo@naver.com"
}
```

- 선택한 유저 삭제
- 실행 예시
```json 
DELETE : localhost:8080/members/1
        
응답 : 200 OK
```

### Lv 3
### 회원가입
- 유저에 비밀번호 필드를 추가
- 실행 예시(사용자 CRUD 중 생성 예시)
```json 
POST : localhost:8080/members/signup

요청 body :
{
"name" : "조예인",
"email" : "codingtrip@naver.com",
"password" : "0000"
}

응답 body :
{
"id": 1,
"name": "조예인",
"email": "codingtrip@naver.com"
}
```

- 선택한 유저 비밀번호 수정
- 실행 예시
```json 
PATCH : localhost:8080/members/1/password
        
요청 body :
{
"oldPassword" : "0000",
"newPassword" : "1111"
}

응답 : 200 OK
```
### Lv 4
### 로그인(인증)
- 실행 예시(로그인)
```json 
POST : localhost:8080/login
요청 body :
{
"email" : "jo@naver.com",
"password" : "0000"
}

응답 : 200 OK
```
- 실행 예시(로그아웃)
```json 
POST : localhost:8080/logout

응답 : 200 OK
```

### Lv 5
### 다양한 예외처리 적용하기
- Validation을 활용해 다양한 예외처리를 적용하기
- 실행 예시(유저 생성) : 이메일 형식
```json 
POST : localhost:8080/members/signup
요청 body :
{
    "name": "study2",
    "email": "study2naver.com",
    "password" : "2222"
}
        
응답 body :
{
    "code": 400,
    "message": "email 형식이 올바르지 않습니다.",
    "status": "BAD_REQUEST"
}
```

- 실행 예시(유저 생성) : 본인(작성자)이 아닌 경우 예외 처리
```json 
GET : localhost:8080/schedules/5
요청 body :
{
    "title": "스프링",
    "contents":"스케줄 생성 과제하기"
}
        
응답 body :
{
    "code": "403",
    "message": "권한이 없습니다.",
    "errors": [
        {
            "code": "NOT_OWNER",
            "message": "본인(작성자)이 아닌 경우 권한이 없습니다."
        }
    ],
    "timestamp": "2025-02-12T16:04:10.769017"
}
```
### Lv 6
### 비밀번호 암호화
- 실행 예시
```json 
POST : localhost:8080/members/signup
        
요청 body :
{
    "name": "study2",
    "email": "study2@naver.com",
    "password" : "2222"
}
        
응답 body :
{
    "id": 3,
    "name": "study2",
    "email": "study2@naver.com"
}

DB에서 비밀번호 조회 결과 예시
: $2a$04$Qek9tovbxswBIM42QJ/cvOInPQ0tk3iKzLBnsZEnhTkLG9BD9lghm
```

### Lv 7
### 댓글 CRUD
- 댓글 생성
- 실행 예시
```json 
POST : localhost:8080/schedules/5/comments
        
요청 body :
{
    "contents" : "열공하세요 ㅎㅅㅎ"
}
        
응답 body :
{
    "id": 7,
    "contents": "열공하세요 ㅎㅅㅎ",
    "scheduleTitle": "JPA",
    "scheduleContents": "JPA 공부하기",
    "memberName": "study",
    "memberEmail": "study@naver.com",
    "createdAt": "2025-02-12T16:11:29.424624",
    "modifiedAt": "2025-02-12T16:11:29.424624"
}
```
- 전체 댓글 조회
- 실행 예시
```json 
GET : localhost:8080/schedules/5/comments

응답 body :
[
    {
        "id": 1,
        "scheduleTitle": "JPA",
        "scheduleContents": "JPA 공부하기",
        "memberName": "study",
        "memberEmail": "study@naver.com",
        "contents": "열공하세요 ㅎㅅㅎ",
        "createdAt": "2025-02-12T16:08:36.96486",
        "modifiedAt": "2025-02-12T16:08:36.96486"
    },
    {
        "id": 2,
        "scheduleTitle": "JPA",
        "scheduleContents": "JPA 공부하기",
        "memberName": "study",
        "memberEmail": "study@naver.com",
        "contents": "열공하세요 ㅎㅅㅎ",
        "createdAt": "2025-02-12T16:08:38.216058",
        "modifiedAt": "2025-02-12T16:08:38.216058"
    },
    {
        "id": 3,
        "scheduleTitle": "JPA",
        "scheduleContents": "JPA 공부하기",
        "memberName": "study",
        "memberEmail": "study@naver.com",
        "contents": "열공하세요 ㅎㅅㅎ",
        "createdAt": "2025-02-12T16:08:38.650363",
        "modifiedAt": "2025-02-12T16:08:38.650363"
    },
    {
        "id": 4,
        "scheduleTitle": "JPA",
        "scheduleContents": "JPA 공부하기",
        "memberName": "study",
        "memberEmail": "study@naver.com",
        "contents": "열공하세요 ㅎㅅㅎ",
        "createdAt": "2025-02-12T16:08:38.961219",
        "modifiedAt": "2025-02-12T16:08:38.961219"
    },
    {
        "id": 7,
        "scheduleTitle": "JPA",
        "scheduleContents": "JPA 공부하기",
        "memberName": "study",
        "memberEmail": "study@naver.com",
        "contents": "열공하세요 ㅎㅅㅎ",
        "createdAt": "2025-02-12T16:11:29.424624",
        "modifiedAt": "2025-02-12T16:11:29.424624"
    }
]
```

- 선택 댓글 조회
- 실행 예시
```json 
GET : localhost:8080/schedules/comments/7
        
응답 body :
{
    "id": 7,
    "scheduleTitle": "JPA",
    "scheduleContents": "JPA 공부하기",
    "memberName": "study",
    "memberEmail": "study@naver.com",
    "contents": "열공하세요 ㅎㅅㅎ",
    "createdAt": "2025-02-12T16:11:29.424624",
    "modifiedAt": "2025-02-12T16:11:29.424624"
}
```
- 선택한 댓글 수정(내용만 수정)
- 실행 예시
```json 
PATCH : localhost:8080/schedules/comments/7
        
요청 body :
{
    "contents":"화이팅입니다 ㅎㅅㅎ"
}

응답 body :
{
    "id": 7,
    "scheduleTitle": "JPA",
    "scheduleContents": "JPA 공부하기",
    "memberName": "study",
    "memberEmail": "study@naver.com",
    "contents": "화이팅입니다 ㅎㅅㅎ",
    "createdAt": "2025-02-12T16:11:29.424624",
    "modifiedAt": "2025-02-12T16:11:29.424624"
}
```
- 선택한 댓글 삭제
- 실행 예시
```json 
DELETE : localhost:8080/schedules/comments/7

응답 : 200 OK
```

### Lv 8
### 일정 페이징 조회
- 할일 제목, 할일 내용, 댓글 개수, 일정 작성일, 일정 수정일, 일정 작성 유저명 필드를 조회
- 실행 예시
```json 
GET : localhost:8080/schedules?page=3&size=3
요청 Param :
page 3
size 3
        
응답 body :
{
"content": [
    {
    "id": 7,
    "title": "JPA3",
    "contents": "JPA 공부하기3",
    "countComment": 0,
    "createdAt": "2025-02-12T16:03:49.407384",
    "modifiedAt": "2025-02-12T16:03:49.407384",
    "memberName": "study"
    },
    {
    "id": 6,
    "title": "JPA2",
    "contents": "JPA 공부하기2",
    "countComment": 2,
    "createdAt": "2025-02-12T16:02:35.232105",
    "modifiedAt": "2025-02-12T16:02:35.232105",
    "memberName": "study2"
    },
    {
    "id": 5,
    "title": "JPA",
    "contents": "JPA 공부하기",
    "countComment": 4,
    "createdAt": "2025-02-12T16:02:32.186407",
    "modifiedAt": "2025-02-12T16:02:32.186407",
    "memberName": "study2"
    }
],
"pageNumber": 3,
"pageSize": 3,
"totalPages": 4,
"totalElements": 12
}
```

## 💥 한계점

- 연관 관계 데이터 삭제 시, 연관된 데이터 모두 삭제 설계
: (soft delete 필요할 것으로 보임)

## 📚 STACKS
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>

## 📞 Contact
- [🚗 Visit codingTrip blog](https://codingtrip.tistory.com/)
- [🙋‍♂️ Visit codingTrip's Github](https://github.com/codingTrip-IT)


