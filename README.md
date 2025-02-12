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
 기능               | Method | URL             | Request              | Response | 상태코드    
|------------------|--------|-----------------|----------------------|----------|---------|
| 회원 생성(회원 가입)     | POST   | /members/signup | 요청 body              | 등록 정보    | 201 CREATED |
| 전체 회원 조회         | GET    | /members          | 요청 param             | 다건 응답 정보 | 200: OK |
| 선택 회원 조회         | GET    | /members/{memberId} | path memberId        | 단건 응답 정보 | 200: OK |
| 선택 회원 <br/>회원명,이메일 수정 | PATCH    | /members/{memberId} | path memberId, 요청 body | 수정 정보    | 200: OK |
| 선택 회원 <br/>비밀번호 수정    | PATCH    | /members/{memberId}/password | path memberId, 요청 body | 수정 정보    | 200: OK |
| 선택 회원 삭제         | DELETE    | /members/{memberId} | path memberId           | -        | 200: OK |


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
| 댓글 생성(해당 일정)           | POST   | /schedules/{scheduleId}/comments  | path scheduleId, 요청 body | 등록 정보    | 201 CREATED |
| 전체 댓글 조회(해당 일정) | GET    | /schedules/{scheduleId}/comments              | path scheduleId | 다건 응답 정보 | 200: OK |
| 선택 댓글 조회        | GET    | /schedules/comments/{commentId} | path commentId | 단건 응답 정보 | 200: OK |
| 선택 댓글 수정        | PATCH  | /schedules/comments/{commentId} | path commentId, 요청 body | 수정 정보    | 200: OK |
| 선택 댓글 삭제        | DELETE | /schedules/comments/{commentId} | path commentId | -        | 200: OK |

## 👩 ERD
<img width="707" alt="Image" src="https://github.com/user-attachments/assets/0cdc04aa-b139-4726-97c1-d82f12201d1f" />

## 👩🏻‍🏫 레벨별 설명
### Lv 1

### 일정 CRUD
- 일정 생성(일정 작성하기)
- 실행 예시
```json 
POST : localhost:8080/schedules
요청 body :
{
    "todo" : "jdbc공부3",
    "userId" : 4,
    "password" : "4444"
}
        
응답 body :
{
    "scheduleId": 21,
    "todo": "jdbc공부3",
    "userId": 4,
    "createdAt": "2025-02-03T18:19:57.370917",
    "updatedAt": "2025-02-03T18:19:57.370917"
}
```
- 전체 일정 조회(등록된 일정 불러오기)
- 실행 예시
```json 
GET : localhost:8080/schedules?userId=2&updatedAt=2025-02-03
요청 param :
userId=2
updatedAt=2025-02-03

응답 body :
[{
    "scheduleId": 17,
    "todo": "스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부1",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T17:20:57",
    "updatedAt": "2025-02-03T17:20:57"
},
{
    "scheduleId": 16,
    "todo": "스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T17:20:53",
    "updatedAt": "2025-02-03T17:20:53"
}]
```

- 선택 일정 조회(선택한 일정 정보 불러오기)
- 실행 예시
```json 
GET : localhost:8080/schedules/4
        
응답 body :
{
    "scheduleId": 4,
    "todo": "스프링공부하기",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T16:19:37",
    "updatedAt": "2025-02-03T16:19:37"
}
```
- 선택한 일정 수정
- 실행 예시
```json 
PATCH : localhost:8080/schedules/4
요청 body :
{
    "todo": "하루 1번 공부하기",
    "password":"2222"
}

응답 body :
{
    "scheduleId": 4,
    "todo": "하루 1번 공부하기",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T16:19:37",
    "updatedAt": "2025-02-03T18:39:56"
}
```
- 선택한 일정 삭제
- soft delete 사용
- 실행 예시
```json 
DELETE : localhost:8080/schedules/4
요청 body :
{
    "password":"2222" 
}
        
응답 : 200 OK
```

### Lv 2
### 유저 CRUD
- 일정 생성(일정 작성하기)
- 실행 예시
```json 
POST : localhost:8080/schedules
요청 body :
{
    "todo" : "jdbc공부3",
    "userId" : 4,
    "password" : "4444"
}
        
응답 body :
{
    "scheduleId": 21,
    "todo": "jdbc공부3",
    "userId": 4,
    "createdAt": "2025-02-03T18:19:57.370917",
    "updatedAt": "2025-02-03T18:19:57.370917"
}
```
- 전체 일정 조회(등록된 일정 불러오기)
- 실행 예시
```json 
GET : localhost:8080/schedules?userId=2&updatedAt=2025-02-03
요청 param :
userId=2
updatedAt=2025-02-03

응답 body :
[{
    "scheduleId": 17,
    "todo": "스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부1",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T17:20:57",
    "updatedAt": "2025-02-03T17:20:57"
},
{
    "scheduleId": 16,
    "todo": "스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T17:20:53",
    "updatedAt": "2025-02-03T17:20:53"
}]
```

- 선택 일정 조회(선택한 일정 정보 불러오기)
- 실행 예시
```json 
GET : localhost:8080/schedules/4
        
응답 body :
{
    "scheduleId": 4,
    "todo": "스프링공부하기",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T16:19:37",
    "updatedAt": "2025-02-03T16:19:37"
}
```
- 선택한 일정 수정
- 실행 예시
```json 
PATCH : localhost:8080/schedules/4
요청 body :
{
    "todo": "하루 1번 공부하기",
    "password":"2222"
}

응답 body :
{
    "scheduleId": 4,
    "todo": "하루 1번 공부하기",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T16:19:37",
    "updatedAt": "2025-02-03T18:39:56"
}
```
- 선택한 일정 삭제
- soft delete 사용
- 실행 예시
```json 
DELETE : localhost:8080/schedules/4
요청 body :
{
    "password":"2222" 
}
        
응답 : 200 OK
```

### Lv 3
### 회원가입
- 작성자와 일정의 연결
- 사용자(작성자) 테이블을 생성 -> 일정 테이블에 FK를 생성해 연관관계 설정
- 실행 예시(사용자 CRUD 중 생성 예시)
```json 
POST : localhost:8080/users
요청 body :
{
    "userName" : "jdbc",
    "email" : "jdbc@gnaver.com"
}

응답 body :
{
    "userId": 4,
    "userName": "jdbc",
    "email": "jdbc@gnaver.com",
    "createdAt": "2025-02-03T18:02:48.368536",
    "updatedAt": "2025-02-03T18:02:48.368536"
}
```
### Lv 4
### 로그인(인증)
- 등록된 일정 목록을 `페이지 번호`와 `크기`를 기준으로 모두 조회
- 실행 예시
```json 
GET : localhost:8080/schedules/paging?pageNo=3&pageSize=3
요청 param :
pageNo=3
pageSize=3
        
응답 body :
[
{
    "scheduleId": 15,
    "todo": "스프링공부하기9",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T16:44:01",
    "updatedAt": "2025-02-03T16:44:01"
    },
    {
    "scheduleId": 14,
    "todo": "스프링공부하기8",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T16:43:47",
    "updatedAt": "2025-02-03T16:43:47"
    },
    {
    "scheduleId": 13,
    "todo": "스프링공부하기7",
    "userId": 2,
    "userName": "spring2",
    "createdAt": "2025-02-03T16:43:21",
    "updatedAt": "2025-02-03T16:43:21"
}
]
```

### Lv 5
### 다양한 예외처리 적용하기
- 수정, 삭제 시 요청할 때 보내는 `비밀번호`가 일치하지 않을 때 예외가 발생합니다.
- 실행 예시
```json 
GET : localhost:8080/schedules/5
요청 body :
{
"password":"1111"
}        
        
응답 body :
{
    "code": "4000",
    "message": "잘못된 요청입니다.",
    "errors": [
        {
            "code": "password",
            "message": "비밀번호가 일치하지 않습니다."
        }
    ],
    "timestamp": "2025-02-03T18:55:27.165996"
}
```
- 선택한 일정 정보를 조회할 수 없을 때 예외가 발생합니다.
    1. 잘못된 정보로 조회하려고 할 때
    2. 이미 삭제된 정보를 조회하려고 할 때
- 실행 예시(잘못된 정보 조회)
```json 
GET : localhost:8080/schedules/100
        
응답 body :
    {
    "code": "4004",
    "message": "요청한 리소스를 찾을 수 없습니다.",
    "errors": [
        {
            "code": "id",
            "message": "잘못된 정보입니다. 다시 입력하세요"
        }
    ],
    "timestamp": "2025-02-03T18:53:21.781344"
}
```
- 실행 예시(이미 삭제된 정보 조회)
```json 
GET : localhost:8080/schedules/4
        
응답 body :
{
    "code": "4004",
    "message": "요청한 리소스를 찾을 수 없습니다.",
    "errors": [
        {
            "code": "deleted",
            "message": "이미 삭제된 정보입니다. 다시 입력하세요"
        }
    ],
    "timestamp": "2025-02-03T18:49:02.986438"
}
```
### Lv 6
### 비밀번호 암호화
- 할일은 최대 200자 이내로 제한, 필수값 처리
- 실행 예시
```json 
GET : localhost:8080/schedules
요청 body :
{
    "todo" : "스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부",
    "userId" : 4,
    "password" : "4444"
}
응답 body :
{
    "timestamp": "2025-02-03T09:59:07.772+00:00",
    "status": 400,
    "error": "Bad Request",
    "path": "/schedules"
}
```
- 비밀번호는 필수값 처리
- 실행 예시
```json 
GET : localhost:8080/schedules
요청 body :
{
    "todo" : "스프링",
    "userId" : 4,
    "password" : ""
}
응답 body :
{
    "timestamp": "2025-02-03T10:01:11.900+00:00",
    "status": 400,
    "error": "Bad Request",
    "path": "/schedules"
}
```

- 이메일 정보가 형식에 맞는지 확인
- 실행 예시
```json 
GET : localhost:8080/users
요청 body :
{
    "userName" : "jdbc",
    "email" : "jdbcnaver.com"
}
응답 body :
{
    "timestamp": "2025-02-03T10:02:01.926+00:00",
    "status": 400,
    "error": "Bad Request",
    "path": "/users"
}
```
### Lv 7
### 댓글 CRUD
- 할일은 최대 200자 이내로 제한, 필수값 처리
- 실행 예시
```json 
GET : localhost:8080/schedules
요청 body :
{
    "todo" : "스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부",
    "userId" : 4,
    "password" : "4444"
}
응답 body :
{
    "timestamp": "2025-02-03T09:59:07.772+00:00",
    "status": 400,
    "error": "Bad Request",
    "path": "/schedules"
}
```

### Lv 8
### 일정 페이징 조회
- 할일은 최대 200자 이내로 제한, 필수값 처리
- 실행 예시
```json 
GET : localhost:8080/schedules
요청 body :
{
    "todo" : "스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부스프링공스프링공부스프링공부스프링공부스프링공부스프링공부스프링공부",
    "userId" : 4,
    "password" : "4444"
}
응답 body :
{
    "timestamp": "2025-02-03T09:59:07.772+00:00",
    "status": 400,
    "error": "Bad Request",
    "path": "/schedules"
}
```



## 💥 한계점

- 연관 soft delete


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


