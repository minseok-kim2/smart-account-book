package kr.ac.shinhan;

public class Account {
	private int id;
	private String type;        // income(수입) / expense(지출)
	private String category;    // 카테고리
	private int amount;         // 금액
	private String description; // 설명
	private String accountDate; // 거래일자
	private String createdDate; // 등록일시
	
	// Getter & Setter
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }
	
	public int getAmount() { return amount; }
	public void setAmount(int amount) { this.amount = amount; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getAccountDate() { return accountDate; }
	public void setAccountDate(String accountDate) { this.accountDate = accountDate; }
	
	public String getCreatedDate() { return createdDate; }
	public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
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
