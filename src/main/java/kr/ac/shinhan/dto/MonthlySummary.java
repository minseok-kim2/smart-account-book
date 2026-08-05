package kr.ac.shinhan.dto;

public record MonthlySummary(String yearMonth, long totalIncome, long totalExpense) {

	public long balance() {
		return totalIncome - totalExpense;
	}

	// record 접근자는 total() 형태라 getTotal() 이 아님.
	// Tomcat 10.1 의 Jakarta EL 5.0 이 record 접근자를 프로퍼티로 인식하지 못해
	// JSP 의 ${item.total} 표기가 동작하려면 getXxx() 형태 getter 가 별도로 필요함.
	public String getYearMonth() {
		return yearMonth;
	}

	public long getTotalIncome() {
		return totalIncome;
	}

	public long getTotalExpense() {
		return totalExpense;
	}

	public long getBalance() {
		return balance();
	}
}
