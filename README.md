[README.md](https://github.com/user-attachments/files/27896336/README.md)
# Smart Account Book

MySQL과 Spring Boot 기반의 스마트 가계부 웹 애플리케이션

---

## 프로젝트 소개

수입과 지출을 손쉽게 관리하고, 도표를 통해 소비 패턴을 시각적으로 확인할 수 있는 가계부 웹 서비스입니다.
Spring Boot MVC 패턴을 기반으로 구성되었으며, MySQL을 통해 데이터를 관리합니다.

---

## 기술 스택

| 구분 | 기술 |
|------|------|
| Backend | Java, Spring Boot |
| Architecture | MVC 패턴 |
| Database | MySQL, JPA |
| Frontend | JSP, HTML |
| Build Tool | Gradle |

---

## 프로젝트 구조

```
src/
├── main/
│   ├── java/kr/ac/shinhan/
│   │   ├── JwbookApplication.java
│   │   ├── Product.java
│   │   ├── ProductService.java
│   │   └── controller/
│   │       ├── StartController.java
│   │       ├── ProductController.java
│   │       ├── CalcController.java
│   │       └── CalculatorController.java
│   └── resources/
│       └── static/
│           └── index.html
└── webapp/
    └── WEB-INF/views/
        ├── mainStart.jsp
        ├── calculator.jsp
        └── ch08/
            ├── productList.jsp
            └── productInfo.jsp
```

---

## 주요 기능

| 기능 | 설명 |
|------|------|
| 수입/지출 입력 | 항목별 수입 및 지출 내역 등록 |
| 목록 조회 | 등록된 가계부 내역 전체 조회 |
| 도표 시각화 | 소비 패턴을 차트로 시각적으로 확인 |
| 계산기 | 금액 계산 기능 제공 |

---

## DB 스키마

```sql
CREATE TABLE account(
    id           INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    type         VARCHAR(10)  NOT NULL COMMENT '수입/지출 구분 (income/expense)',
    category     VARCHAR(50)  NOT NULL COMMENT '카테고리',
    amount       INT          NOT NULL COMMENT '금액',
    description  VARCHAR(200)          COMMENT '설명',
    account_date DATE         NOT NULL COMMENT '거래일자',
    created_date TIMESTAMP    DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일시'
);
```

| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | INT | 기본키, 자동 증가 |
| type | VARCHAR(10) | 수입/지출 구분 (income/expense) |
| category | VARCHAR(50) | 카테고리 |
| amount | INT | 금액 |
| description | VARCHAR(200) | 설명 (선택) |
| account_date | DATE | 거래일자 |
| created_date | TIMESTAMP | 등록일시 (자동 입력) |

---

## 실행 방법

**1. 레포지토리 클론**

```bash
git clone https://github.com/minseok-kim2/smart-account-book.git
cd smart-account-book
```

**2. DB 설정**

`src/main/resources/application.properties`에서 MySQL 연결 정보를 설정합니다.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/accountbook
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**3. 빌드 및 실행**

```bash
./gradlew bootRun
```

**4. 접속**

```
http://localhost:8080
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
