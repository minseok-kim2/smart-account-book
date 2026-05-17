📒 Smart Account Book

MySQL과 Spring Boot 기반의 스마트 가계부 웹 애플리케이션


📌 프로젝트 소개
수입과 지출을 손쉽게 관리하고, 도표를 통해 소비 패턴을 시각적으로 확인할 수 있는 가계부 웹 서비스입니다.
Spring Boot MVC 패턴을 기반으로 구성되었으며, MySQL을 통해 데이터를 관리합니다.

🛠 기술 스택
구분기술BackendJava, Spring BootArchitectureMVC 패턴DatabaseMySQL, JPAFrontendJSP, HTMLBuild ToolGradle

📁 프로젝트 구조
src/
├── main/
│   ├── java/kr/ac/shinhan/
│   │   ├── JwbookApplication.java       # 애플리케이션 진입점
│   │   ├── Product.java                 # 상품/항목 도메인
│   │   ├── ProductService.java          # 비즈니스 로직
│   │   └── controller/
│   │       ├── StartController.java     # 메인 페이지
│   │       ├── ProductController.java   # 수입/지출 관리
│   │       ├── CalcController.java      # 계산 기능
│   │       └── CalculatorController.java
│   └── resources/
│       └── static/
│           └── index.html               # 메인 페이지
└── webapp/
    └── WEB-INF/views/
        ├── mainStart.jsp                # 시작 화면
        ├── calculator.jsp               # 계산기
        └── ch08/
            ├── productList.jsp          # 목록 조회
            └── productInfo.jsp          # 상세 정보

✨ 주요 기능
기능설명수입/지출 입력항목별 수입 및 지출 내역 등록목록 조회등록된 가계부 내역 전체 조회도표 시각화소비 패턴을 차트로 시각적으로 확인계산기금액 계산 기능 제공

🗄️ DB 스키마
sqlCREATE TABLE account(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(10) NOT NULL COMMENT '수입/지출 구분 (income/expense)',
    category VARCHAR(50) NOT NULL COMMENT '카테고리',
    amount INT NOT NULL COMMENT '금액',
    description VARCHAR(200) COMMENT '설명',
    account_date DATE NOT NULL COMMENT '거래일자',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일시'
);
컬럼타입설명idINT기본키, 자동 증가typeVARCHAR(10)수입/지출 구분 (income/expense)categoryVARCHAR(50)카테고리amountINT금액descriptionVARCHAR(200)설명 (선택)account_dateDATE거래일자created_dateTIMESTAMP등록일시 (자동 입력)

⚙️ 실행 방법
1. 레포지토리 클론
bashgit clone https://github.com/minseok-kim2/smart-account-book.git
cd smart-account-book
2. DB 설정
src/main/resources/application.properties에서 MySQL 연결 정보를 설정합니다.
propertiesspring.datasource.url=jdbc:mysql://localhost:3306/accountbook
spring.datasource.username=your_username
spring.datasource.password=your_password
3. 빌드 및 실행
bash./gradlew bootRun
4. 접속
http://localhost:8080

👨‍💻 개발자
김민석풀스택 개발 (단독)

📝 개발 후기
Spring Boot와 MySQL을 처음으로 혼자 연동해보며 MVC 패턴의 흐름을 직접 익힌 프로젝트입니다.
도표 시각화를 통해 단순한 CRUD를 넘어 사용자에게 의미 있는 정보를 제공하는 경험을 쌓았습니다.
