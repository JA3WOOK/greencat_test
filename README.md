# 📌 ToDo / Calendar API

## 📝 개요

본 프로젝트는 **할일(ToDo) 이벤트**와 **일정(Calendar) 이벤트**를 관리할 수 있는 백엔드 API 서버입니다.
 사용자는 할일과 일정을 **생성**, **조회**, **수정**, **삭제**할 수 있으며, 일정 이벤트는 추가적으로 **다른 사용자**에게 공유 할 수 있습니다.

---

## 🚀 실행 방법

```bash
# 1. 프로젝트 클론
git clone https://github.com/JA3WOOK/greencat_test.git

# 2. 환경 설정
# application.properties에 있는 DB 정보 기반 연결

# 3. 백엔드 실행
src>main>java>GreencatApplication Run 실행 후 schema.sql, data.sql 파일 통해
DB에 테이블과 테스트 데이터 자동생성

# 4. 프론트엔드 실행
터미널에서 cd greencat-frontend 이동 후
npm install
npm start

```

---

## 🧰 사용 기술 스택

- Amazon Corretto 19
- Spring Boot 3.4.4
- Spring Data JPA
- MySQL
- Hibernate
- Lombok
- Maven
- Swagger 

---

## 📚 주요 라이브러리

| 라이브러리                     | 설명                           |
| ------------------------------ | ------------------------------ |
| `spring-boot-starter-data-jpa` | ORM을 통한 데이터 접근         |
| `spring-boot-starter-web`      | REST API 개발용                |
| `mysql-connector-j`            | MySQL 연결 드라이버            |
| `lombok`                       | 코드 간소화를 위한 어노테이션  |
| `springdoc-openapi-ui`         | API 문서화를 위한 Swagger 대체 |

---

## 📡 API 명세



###  어플리케이션 실행 후 

### http://localhost:8080/swagger-ui/index.html#/  접속



---

## 🚀 테스트 케이스

```bash
# 1. Calendar 단위테스트

src>test>com.example.todocalendar>calendar>controller & service
										   
CalendarEventControllerTest & CaendarEventServiceTest 
각각 Run Test 실행

# 2. Todo 단위테스트

src>test>com.example.todocalendar>todo>controller & service
										   
TodoContorllerTest & TodoServiceTest 
각각 Run Test 실행
```



---

## 💡 추가 기능

- ✅ 완료 여부 필터링
- 🧭 Swagger UI (`/swagger-ui.html`) 제공
- 👥 **이메일 기반 일정 공유 기능**  
  - 일정에 대해 여러 사용자 이메일로 공유 가능  
  - 공유된 사용자 목록 저장 및 조회 가능

---
