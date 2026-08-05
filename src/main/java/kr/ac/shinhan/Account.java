package kr.ac.shinhan;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data // getter and Setter Support
public class Account {
	private int id;
	private String type;        // income(수입) / expense(지출)
	private String category;    // 카테고리
	private long amount;        // 금액 (원 단위, int 상한 약 21억을 넘을 수 있어 long 사용)
	private String description; // 설명

	// <input type="date"> 가 보내는 ISO(yyyy-MM-dd) 형식 바인딩을 코드로 명시
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate accountDate; // 거래일자

	private LocalDateTime createdDate; // 등록일시
}
/*
CREATE TABLE account (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	type VARCHAR(10) NOT NULL COMMENT '수입/지출 구분 (income/expense)',
	category VARCHAR(50) NOT NULL COMMENT '카테고리',
	amount INT NOT NULL COMMENT '금액',
	description VARCHAR(200) COMMENT '설명',
	account_date DATE NOT NULL COMMENT '거래일자',
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일시'
);

-- 샘플 데이터
INSERT INTO account (type, category, amount, description, account_date) VALUES
('income', '급여', 3000000, '12월 급여', '2025-12-01'),
('expense', '식비', 15000, '점심 식사', '2025-12-02'),
('expense', '교통비', 5000, '버스/지하철', '2025-12-02'),
('expense', '쇼핑', 50000, '옷 구매', '2025-12-03'),
('income', '용돈', 100000, '부모님 용돈', '2025-12-05'),
('expense', '식비', 25000, '저녁 회식', '2025-12-05'),
('expense', '문화생활', 15000, '영화 관람', '2025-12-06'),
('expense', '통신비', 55000, '핸드폰 요금', '2025-12-07');
*/
