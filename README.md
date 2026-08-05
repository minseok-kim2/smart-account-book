# Smart Account Book

MySQL과 Spring Boot 기반의 스마트 가계부 웹 애플리케이션

---

## 프로젝트 소개

수입과 지출을 손쉽게 관리하고, 도표를 통해 소비 패턴을 시각적으로 확인할 수 있는 가계부 웹 서비스입니다.
간단한 게시판 기능도 함께 포함되어 있습니다.
Spring Boot MVC 패턴을 기반으로 구성되었으며, MySQL을 통해 데이터를 관리합니다.

---

## 기술 스택

| 구분 | 기술 |
|------|------|
| Backend | Java 21, Spring Boot 3.5 |
| Architecture | MVC 패턴 (Controller - Service - DAO) |
| Database | MySQL, Spring JDBC (JdbcTemplate) |
| Frontend | JSP, Bootstrap, Chart.js |
| Build Tool | Gradle |

---

## 프로젝트 구조

```
src/main/
├── java/kr/ac/shinhan/
│   ├── JwbookApplication.java
│   ├── Account.java
│   ├── AccountDAO.java
│   ├── Board.java
│   ├── BoardDAO.java
│   ├── controller/
│   │   ├── StartController.java
│   │   ├── AccountController.java
│   │   └── BoardController.java
│   ├── service/
│   │   └── AccountService.java
│   └── dto/
│       ├── MonthlySummary.java
│       ├── CategorySum.java
│       └── MonthlyTrend.java
├── resources/
│   ├── application.properties        (git 추적 제외, 직접 생성 필요)
│   ├── application-example.properties (설정 예시 및 필요한 환경변수 안내)
│   └── static/index.html
└── webapp/WEB-INF/views/
    ├── mainStart.jsp
    ├── account/
    │   ├── accountList.jsp
    │   ├── accountForm.jsp
    │   ├── accountEdit.jsp
    │   └── accountStats.jsp
    └── board/
        ├── boardList.jsp
        ├── boardForm.jsp
        ├── boardEdit.jsp
        └── boardView.jsp
```

---

## 주요 기능

| 기능 | 설명 |
|------|------|
| 수입/지출 입력 | 항목별 수입 및 지출 내역 등록·수정·삭제 |
| 월별 목록 조회 | 월을 선택해 해당 월의 내역과 수입/지출/잔액 요약 확인 |
| 통계 시각화 | 카테고리별 지출 비중(도넛 차트), 최근 6개월 수입/지출 추이(막대 차트) |
| 게시판 | 게시글 작성·조회·수정·삭제, 조회수 카운트 |

---

## DB 스키마

```sql
CREATE TABLE account (
    id           INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    type         VARCHAR(10)  NOT NULL COMMENT '수입/지출 구분 (income/expense)',
    category     VARCHAR(50)  NOT NULL COMMENT '카테고리',
    amount       BIGINT       NOT NULL COMMENT '금액',
    description  VARCHAR(200)          COMMENT '설명',
    account_date DATE         NOT NULL COMMENT '거래일자',
    created_date TIMESTAMP    DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일시',
    CONSTRAINT chk_account_type CHECK (type IN ('income', 'expense'))
);
CREATE INDEX idx_account_date ON account (account_date);
CREATE INDEX idx_account_type_date ON account (type, account_date);

CREATE TABLE board (
    id           INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(200) NOT NULL,
    writer       VARCHAR(50)  NOT NULL,
    content      TEXT         NOT NULL,
    created_date TIMESTAMP    DEFAULT CURRENT_TIMESTAMP(),
    view_count   INT          DEFAULT 0
);
```

| 컬럼 (account) | 타입 | 설명 |
|------|------|------|
| id | INT | 기본키, 자동 증가 |
| type | VARCHAR(10) | 수입/지출 구분 (income/expense) |
| category | VARCHAR(50) | 카테고리 |
| amount | BIGINT | 금액 |
| description | VARCHAR(200) | 설명 (선택) |
| account_date | DATE | 거래일자 |
| created_date | TIMESTAMP | 등록일시 (자동 입력) |

기존 DB에 이미 `account` 테이블이 있다면 [migration.sql](migration.sql)을 적용하세요 (amount 컬럼 타입 변경, CHECK 제약, 인덱스 추가).

---

## 실행 방법

**1. 레포지토리 클론**

```bash
git clone https://github.com/minseok-kim2/smart-account-book.git
cd smart-account-book
```

**2. DB 설정**

DB 자격증명은 코드/설정 파일에 직접 적지 않고 환경변수로 주입합니다. 실행 전 아래 환경변수를 설정하세요 (자세한 목록은 [application-example.properties](src/main/resources/application-example.properties) 참고).

```
DB_USERNAME=root
DB_PASSWORD=your_password
DB_HOST=localhost   # 선택, 기본값 localhost
DB_PORT=3306        # 선택, 기본값 3306
DB_NAME=jwbookdb    # 선택, 기본값 jwbookdb
```

**3. 빌드 및 실행**

```bash
./gradlew bootRun
```

**4. 접속**

```
http://localhost:8091
```

---

## 개발자

| 이름 | 역할 |
|------|------|
| 김민석 | 풀스택 개발 (단독) |

---

## 개발 후기

Spring Boot와 MySQL을 처음으로 혼자 연동해보며 MVC 패턴의 흐름을 직접 익힌 프로젝트입니다.
도표 시각화를 통해 단순한 CRUD를 넘어 사용자에게 의미 있는 정보를 제공하는 경험을 쌓았습니다.
