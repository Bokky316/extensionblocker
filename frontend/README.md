# 📁 Extension Blocker - Frontend

파일 확장자 차단 시스템의 프론트엔드 레포지토리입니다.  
고정 확장자와 커스텀 확장자를 조회하고 관리할 수 있는 UI를 제공합니다.

---

## 📌 주요 기능

- ✅ 고정 확장자 리스트 조회 및 체크/해제
- ✅ 체크 상태 유지 (DB 반영된 상태값 GET)
- ✅ 커스텀 확장자 실시간 입력 검증
  - 영문자만 허용
  - 20자 이하 제한
  - 중복 등록 방지 (`고정` + `커스텀` 확장자 모두 포함)
- ✅ 커스텀 확장자 개수 제한 (최대 200개)
- ✅ 커스텀 확장자 등록 및 삭제
- ✅ UI 상태 새로고침에도 유지

---

## 🧑‍💻 실행 방법

```bash
# 1. frontend 디렉토리로 이동
cd frontend

# 2. 패키지 설치
npm install

# 3. 개발 서버 실행
npm run dev
```

> 기본 실행 포트는 **`3000`**입니다.  
> `.env` 또는 `.env.production` 파일에 아래처럼 백엔드 주소를 설정해주세요:

```env
VITE_API_BASE_URL=http://localhost:8080
```

---

## 🗂 폴더 구조

```
src/
├── assets/
│   └── react.svg
│
├── components/
│   ├── CustomInputForm.tsx        # 커스텀 확장자 입력 폼
│   ├── CustomList.tsx             # 커스텀 확장자 리스트 + 삭제 버튼
│   ├── FixedList.tsx              # 고정 확장자 리스트 + 체크박스
│   └── common/                    # 공통 UI 컴포넌트
│       ├── Button.tsx
│       ├── FormErrorMessage.tsx
│       ├── FormInput.tsx
│       └── Pill.tsx
│
├── hooks/                         # 커스텀 훅 모음
│   ├── useAppDispatch.ts
│   ├── useAppSelector.ts
│   ├── useCustomExtensions.ts
│   └── useFixedExtensions.ts
│
├── services/                      # API 호출 서비스
│   ├── api.ts
│   └── extensionService.ts
│
├── store/                         # Redux 전역 상태관리
│   └── slices/
│       ├── customExtension.ts
│       └── index.ts
│
└── types/                         # 타입 정의
    └── index.ts
```

---

## 🧪 기술 스택

* **React 18 + TypeScript**
* **Redux Toolkit + RTK Query**
* **Zustand**
* **Axios**
* **Vite**
* **Tailwind CSS**

---

## 📝 유효성 및 처리 로직

* 모든 확장자는 **소문자로 통일 (`toLowerCase`)**
* 입력값은 `.trim()` 처리 후 등록
* 커스텀 확장자 등록 시:
  - 고정 + 커스텀 포함 중복 체크
  - 최대 200개 제한
* 삭제 즉시 반영
* 고정 확장자는 삭제되지 않고 `checked` 상태만 변경됨

---

## 📷 UI 구성

| 고정 확장자                  | 커스텀 확장자                           |
| ----------------------- | --------------------------------- |
| ✅ 체크박스 목록<br>✅ 체크 상태 저장 | ✅ 입력 폼<br>✅ 리스트 표시<br>✅ X 버튼으로 삭제 |

---

## 🔗 API 연동 정보

| 메서드    | 경로                            | 설명            |
| --------- | ----------------------------- | --------------- |
| `GET`     | `/api/fixed`                  | 고정 확장자 전체 조회  |
| `PUT`     | `/api/fixed/{id}`             | 고정 확장자 상태 토글  |
| `GET`     | `/api/extensions/custom`      | 커스텀 확장자 목록 조회 |
| `POST`    | `/api/extensions/custom`      | 커스텀 확장자 등록    |
| `DELETE`  | `/api/extensions/custom/{id}` | 커스텀 확장자 삭제    |

---

## 💻 개발 환경

* Node.js 18+
* npm 9+
* Vite 5+
