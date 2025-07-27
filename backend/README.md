# 🔐 파일 확장자 차단 시스템 (Extension Blocker)

Spring Boot 기반으로 구현한 **파일 확장자 차단 백엔드 서버**입니다.  
고정 확장자와 커스텀 확장자 등록 및 차단 기능을 제공합니다.

---

## 🧩 주요 기능

- 고정 확장자 조회 및 상태 변경 (check/uncheck)
- 커스텀 확장자 등록, 삭제
- 커스텀 확장자 중복 방지 및 최대 200개 제한
- 확장자 길이 제한(20자), trim + 소문자 통일 처리
- 고정 확장자는 enum 기반 seed 데이터로 초기 로딩
- 새로고침 시 상태 유지 (DB 기반)
- 공통 응답 구조 사용 (ApiResponse)
- Swagger를 통한 API 문서 자동화
- 단위 테스트 완비 (서비스 & 컨트롤러)

---

## ⚙️ 기술 스택

| 분류       | 사용 기술                          |
|------------|-----------------------------------|
| Language   | Java 17                           |
| Framework  | Spring Boot 3                     |
| ORM        | Spring Data JPA                   |
| DB         | H2 (테스트용) / MariaDB (로컬)     |
| Docs       | springdoc-openapi (Swagger)       |
| Build Tool | Gradle                            |
| Test       | JUnit 5, Mockito                  |
| 기타       | Lombok, Validation, Global Advice |

---

## 🚀 실행 방법

```bash
# 루트 기준
cd extensionblocker/backend

# 실행
./gradlew bootRun
```

> Swagger 접속 주소: [http://15.165.114.113:8080/swagger-ui/index.html](http://15.165.114.113:8080/swagger-ui/index.html)

※ 기본 DB는 로컬 MariaDB 사용  
※ 테스트 실행 시 자동으로 H2(in-memory) DB로 전환됩니다

---

## 📌 API 명세

| 메서드    | 경로                 | 설명                   |
| --------- | ------------------ | ---------------------- |
| `GET`     | `/api/fixed`       | 고정 확장자 전체 조회         |
| `PUT`     | `/api/fixed/{id}`  | 고정 확장자 check/uncheck |
| `GET`     | `/api/custom`      | 커스텀 확장자 전체 조회        |
| `POST`    | `/api/custom`      | 커스텀 확장자 등록           |
| `DELETE`  | `/api/custom/{id}` | 커스텀 확장자 삭제           |

### ✅ 응답 예시 (성공)

```json
{
  "code": 200,
  "message": "요청 성공",
  "data": {
    "id": 1,
    "name": "exe",
    "checked": true
  }
}
```

---

## 🧪 테스트

- 단위 테스트 포함 (`src/test/java`)
- 테스트 대상: `Service`, `Controller` 계층
- 실행:

```bash
./gradlew test
```

> 테스트 실행 시 H2(in-memory) DB가 자동 사용됩니다.  
> MariaDB 없이도 전체 테스트 통과 가능

---

## 📂 프로젝트 구조 요약

```
backend
 ┣ 📂controller        ← API 엔드포인트
 ┣ 📂dto               ← 요청/응답 객체
 ┣ 📂entity            ← JPA 엔티티
 ┣ 📂repository        ← JPA 레포지토리
 ┣ 📂service           ← 비즈니스 로직
 ┣ 📂exception         ← 예외 정의 및 처리
 ┣ 📂config            ← Swagger, Seed 초기화 등 설정
 ┣ 📂common            ← 공통 응답 포맷
 ┗ 📂test              ← JUnit 단위 테스트
```

---

## 🛠 고려한 설계 사항

| 항목        | 설명 |
|-------------|------|
| 고정 확장자 | `FixedExtensionType` enum + `DataInitializer`로 초기화 |
| 커스텀 확장자 | 최대 200개 제한 + 소문자 기준 중복 방지 (DB 유니크) |
| 응답 구조    | `ApiResponse<T>` 통일 포맷 사용 |
| 예외 처리    | `@Valid` + `@ControllerAdvice` |
| 정렬 기준    | 커스텀 확장자 등록일 기준 역순 정렬 |
| 유지보수성   | DTO 분리 + 테스트 기반 구조 |

---

## 👤 개발자 메모

본 프로젝트는 면접 제출용 과제로,  
**기능 충족 + 구조 안정성 + 유지보수 가능성**을 고려해 설계 및 구현했습니다.  
추후 확장성(예: 사용자별 차단 설정, 인증 연동 등)을 염두에 둔 구조입니다.
